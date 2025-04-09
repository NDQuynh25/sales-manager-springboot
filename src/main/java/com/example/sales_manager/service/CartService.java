package com.example.sales_manager.service;

import org.springframework.stereotype.Service;

import com.example.sales_manager.dto.request.CartReq;
import com.example.sales_manager.entity.Cart;
import com.example.sales_manager.entity.User;
import com.example.sales_manager.repository.CartRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /*
     * Handle create cart for user
     */

    public Cart handleCreateCart(CartReq CartReq) {
        Cart cart = new Cart();

        cartRepository.save(cart);
        return cart;
    }

    public Cart handleCreateCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
        return cart;
    }

}
