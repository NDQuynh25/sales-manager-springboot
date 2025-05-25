package com.example.sales_manager.service;

import org.springframework.stereotype.Service;


import com.example.sales_manager.entity.Cart;

import com.example.sales_manager.repository.CartRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    public Cart handleGetCarts(Long userId, Integer page, Integer size, String sort) {
        return cartRepository.findByUserId(userId);
        

    }

}
