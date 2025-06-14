package com.example.sales_manager.service;

import org.springframework.stereotype.Service;

import com.example.sales_manager.entity.Cart;

import com.example.sales_manager.repository.CartRepository;
import com.example.sales_manager.repository.ProductRepository;
import com.example.sales_manager.repository.SkuRepository;
import com.example.sales_manager.dto.ResultPagination;
import com.example.sales_manager.dto.request.CartItemReq;
import com.example.sales_manager.dto.response.CartRes;
import com.example.sales_manager.entity.CartItem;
import com.example.sales_manager.entity.Product;
import com.example.sales_manager.entity.SKU;

import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.example.sales_manager.entity.User;
import com.example.sales_manager.repository.UserRepository;
import com.example.sales_manager.mapper.CartMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final SkuRepository skuRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public CartService(CartRepository cartRepository, UserRepository userRepository, SkuRepository skuRepository, ProductRepository productRepository, CartMapper cartMapper) {

        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.skuRepository = skuRepository;
        this.productRepository = productRepository;
        this.cartMapper = cartMapper;

    }

    


    public CartRes addItemToCart(Long userId, CartItemReq cartItemReq) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be provided and greater than 0");
        }

        if (cartItemReq == null || cartItemReq.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid cart item request");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        Cart cart = user.getCart();
        System.out.println("[INFO] cart ID: " + cart.getId());

        SKU sku = skuRepository.findById(cartItemReq.getSkuId())
                .orElseThrow(() -> new EntityNotFoundException("SKU not found with ID: " + cartItemReq.getSkuId()));
        System.out.println("[INFO] sku ID: " + sku.getId());
        Product product = productRepository.findById(cartItemReq.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + cartItemReq.getProductId()));
        System.out.println("[INFO] product ID: " + product.getId());
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getSku().getId().equals(cartItemReq.getSkuId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + cartItemReq.getQuantity());
        } else {
            CartItem cartItem = CartItem.builder()
                    .sku(sku)
                    .product(product)
                    .quantity(cartItemReq.getQuantity())
                    .cart(cart)
                    .build();
            
            cart.getCartItems().add(cartItem);
        }

        return cartMapper.mapToCartRes(cartRepository.save(cart));
    }

          

}
