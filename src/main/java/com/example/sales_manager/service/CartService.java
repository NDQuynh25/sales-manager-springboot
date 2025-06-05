package com.example.sales_manager.service;

import org.springframework.stereotype.Service;

import com.example.sales_manager.entity.Cart;

import com.example.sales_manager.repository.CartRepository;
import com.example.sales_manager.dto.ResultPagination;
import com.example.sales_manager.entity.CartItem;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {

        this.cartRepository = cartRepository;
    }

    public ResultPagination handleGetCarts(Long userId, Specification<CartItem> spec, Pageable pageable) throws Exception {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be provided and greater than 0");
        }

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new Exception("Cart not found for user ID: " + userId);
        }

        //Page<CartItem> page = cartRepository.findCartItemsByCartId(cart.getId(), spec, pageable);
        
        // ResultPagination resultPagination = ResultPagination.builder()
        //         .meta(new ResultPagination.Meta(
        //                 page.getNumber(), 
        //                 page.getSize(), 
        //                 page.getTotalElements(), 
        //                 page.getTotalPages()     
        //         ))
        //         .result(page.getContent())
        //         .build();    

        ResultPagination resultPagination = new ResultPagination();
        
        return resultPagination;
    }

}
