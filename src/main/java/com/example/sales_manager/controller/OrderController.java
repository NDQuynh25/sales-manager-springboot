package com.example.sales_manager.controller;

import org.springframework.stereotype.Controller;

import com.example.sales_manager.dto.request.CartItemReq;
import com.example.sales_manager.dto.request.OrderReq;
import com.example.sales_manager.dto.response.ApiResponse;
import com.example.sales_manager.dto.response.CartItemRes;
import com.example.sales_manager.entity.CartItem;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.example.sales_manager.service.OrderService;




@Controller
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/user/{userId}/check-cart-items")
    public ResponseEntity<ApiResponse<Object>> checkCartItems (
            @PathVariable Long userId,
            @RequestBody List<CartItemReq> cartItemReqs) throws Exception {

        ApiResponse<Object> response = new ApiResponse<>();
        System.out.println(">>> OrderReq: " + cartItemReqs);

       
        List<CartItemRes> cartItems = orderService.handleCheckCartItems(cartItemReqs);

        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Kiểm tra giỏ hàng thành công");
        response.setData(cartItems);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    
}
