package com.example.sales_manager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.sales_manager.dto.request.CartItemReq;
import com.example.sales_manager.dto.request.OrderItemReq;
import com.example.sales_manager.dto.request.OrderReq;
import com.example.sales_manager.dto.response.CartItemRes;

import com.example.sales_manager.entity.CartItem;
import com.example.sales_manager.entity.Order;
import com.example.sales_manager.entity.OrderItem;

import com.example.sales_manager.entity.Product;
import com.example.sales_manager.entity.SKU;
import com.example.sales_manager.entity.User;
import com.example.sales_manager.repository.CartItemRepository;
import com.example.sales_manager.repository.ProductRepository;
import com.example.sales_manager.repository.UserRepository;
import com.example.sales_manager.repository.OrderRepository;
import com.example.sales_manager.repository.SkuRepository;
import com.example.sales_manager.util.constant.OrderStatusEnum;

import jakarta.transaction.Transactional;

import com.example.sales_manager.mapper.CartItemMapper;

@Service
public class OrderService {

    private final SkuRepository skuRepository;

    private final UserRepository userRessitory;

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final CartItemMapper cartItemMapper;

    private final CartItemRepository cartItemRepository;

    public OrderService(
            SkuRepository skuRepository,
            UserRepository userRessitory,
            OrderRepository orderRepository,
            ProductRepository productRepository,
            CartItemRepository cartItemRepository,
            CartItemMapper cartItemMapper) {
        this.skuRepository = skuRepository;
        this.userRessitory = userRessitory;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
    }

    private SKU validateOrderItem(OrderItemReq orderItemReq) {

        // Validated null, empty, blank in OrderItemReq by annotation in DTO class
        Product product = productRepository.findById(orderItemReq.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại"));

        if (product.getIsActive() != 1) {
            throw new IllegalArgumentException("Sản phẩm không khả dụng");
        }

        if (product.getSkus() == null || product.getSkus().isEmpty() || product.getSkus().size() == 0) {
            throw new IllegalArgumentException("Sản phẩm không có SKU");
        }

        // Tìm SKU theo ID
        SKU sku = product.getSkus().stream()
                .filter(s -> s.getId().equals(orderItemReq.getSkuId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("SKU không tồn tại hoặc không thuộc sản phẩm"));

        // Kiểm tra SKU có hoạt động không
        if (sku.getIsActive() != 1) {
            throw new IllegalArgumentException("SKU không khả dụng");
        }

        // Kiểm tra tồn kho
        if (sku.getStock() < orderItemReq.getQuantity()) {
            throw new IllegalArgumentException("Số lượng SKU không đủ");
        }

        // Kiểm tra giá tiền
        if (sku.getOriginalPrice().compareTo(orderItemReq.getOriginalPriceAtOrderTime()) != 0) {
            throw new IllegalArgumentException("Giá sản phẩm đã thay đổi");
        }

        // Kiểm tra discount
        if (sku.getDiscount().compareTo(orderItemReq.getDiscountAtOrderTime()) != 0) {
            throw new IllegalArgumentException("Chính sách giảm giá đã thay đổi");
        }

        return sku;
    }

    public List<CartItemRes> handleCheckCartItems(List<CartItemReq> cartItemReqs) throws Exception {

        List<CartItem> cartItems = new ArrayList<>();

        if (cartItemReqs == null || cartItemReqs.isEmpty()) {
            throw new IllegalArgumentException("Giỏ hàng không được để trống");
        }

        for (CartItemReq cartItemReq : cartItemReqs) {
            if (cartItemReq.getId() == null) {
                throw new IllegalArgumentException("Mặt hàng không được để trống");
            }
            Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemReq.getId());
            if (optionalCartItem.isEmpty()) {
                throw new IllegalArgumentException("Mặt hàng không tồn tại");
            }

            CartItem cartItem = optionalCartItem.get();

            if (cartItem.getQuantity() <= 0) {
                throw new IllegalArgumentException("Số lượng mặt hàng phải lớn hơn 0");
            }

            if (cartItem.getSku() == null
                    || cartItem.getSku().getId() == null
                    || cartItem.getSku().getId() != cartItemReq.getSkuId()
                    || cartItem.getSku().getId() <= 0
                    || cartItem.getProduct() == null
                    || cartItem.getProduct().getId() == null
                    || cartItem.getProduct().getId() <= 0
                    || cartItem.getProduct().getId() != cartItemReq.getProductId()
                    || cartItem.getProduct().getSkus().contains(cartItem.getSku()) == false) {
                throw new IllegalArgumentException("Mặt hàng không hợp lệ");
            }

            if (cartItem.getSku().getStock() < cartItem.getQuantity()) {
                throw new IllegalArgumentException("Số lượng mặt hàng không đủ");
            }
            cartItems.add(cartItem);
        }

        return cartItems.stream()
                .map(cartItemMapper::mapToCartItemRes).toList();

    }

    @Transactional
    public void handleCreateOrder(Long userId, OrderReq orderReq) throws Exception {

        if (orderReq.getOrderItems() == null || orderReq.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("Danh sách sản phẩm không được rỗng");
        }

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemReq orderItemReq : orderReq.getOrderItems()) {
            SKU sku = validateOrderItem(orderItemReq);

            // Tạo OrderItem từ request và thông tin SKU
            OrderItem orderItem = OrderItem.builder()
                    .sku(sku)
                    .product(sku.getProduct())
                    .quantity(orderItemReq.getQuantity())
                    .originalPriceAtOrderTime(sku.getOriginalPrice())
                    .discountAtOrderTime(sku.getDiscount())
                    .build();

            orderItems.add(orderItem);
        }
        User user = userRessitory.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại"));
        if (user.getIsActive() != 1) {
            throw new IllegalArgumentException("Tài khoản người dùng không khả dụng");
        }

        // Tạo Order entity
        Order order = Order.builder()
                .user(user)
                .recipientName(orderReq.getRecipientName())
                .recipientPhoneNumber(orderReq.getRecipientPhoneNumber())
                .recipientCodeProvince(orderReq.getRecipientCodeProvince())
                .recipientCodeDistrict(orderReq.getRecipientCodeDistrict())
                .recipientCodeWard(orderReq.getRecipientCodeWard())
                .recipientAddress(orderReq.getRecipientAddress())
                .orderItems(orderItems)
                .statusOrder(OrderStatusEnum.PENDING)
                .orderDate(orderReq.getOrderDate())
                .paymentMethod(orderReq.getPaymentMethod())
                .note(orderReq.getNote())
                .build();

        // Lưu Order vào DB
        orderRepository.save(order);
    }
}
