package com.example.sales_manager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.sales_manager.dto.request.CartItemReq;
import com.example.sales_manager.dto.response.CartItemRes;

import com.example.sales_manager.entity.CartItem;

import com.example.sales_manager.repository.CartItemRepository;
import com.example.sales_manager.repository.SkuRepository;
import com.example.sales_manager.mapper.CartItemMapper;

@Service
public class OrderService {
    
    private final SkuRepository skuRepository;

    private final CartItemMapper cartItemMapper;

    private final CartItemRepository cartItemRepository;

    public OrderService(SkuRepository skuRepository, 
            CartItemRepository cartItemRepository,
            CartItemMapper cartItemMapper) {
        this.skuRepository = skuRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
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
                || cartItem.getProduct().getSkus().contains(cartItem.getSku()) == false
            ) {
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
}

