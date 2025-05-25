package com.example.sales_manager.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.sales_manager.service.CartItemService;

import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/v1/cart-items")
public class CartItemController {
    private final CartItemService cartItemService;
    
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }



    
}
