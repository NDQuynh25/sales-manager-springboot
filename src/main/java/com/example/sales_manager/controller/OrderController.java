package com.example.sales_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import com.cloudinary.provisioning.Account;
import com.example.sales_manager.dto.request.CartItemReq;
import com.example.sales_manager.dto.request.OrderReq;
import com.example.sales_manager.dto.response.AccountInfoRes;
import com.example.sales_manager.dto.response.ApiResponse;
import com.example.sales_manager.dto.response.CartItemRes;

import org.apache.catalina.security.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import com.example.sales_manager.service.OrderService;
import com.example.sales_manager.util.SecurityUtils;

import jakarta.validation.Valid;




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
        AccountInfoRes accountInfoRes = (AccountInfoRes) SecurityUtils.getCurrentUser();

        if (accountInfoRes.getId() != userId) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setMessage("Bạn không có quyền thực hiện hành động này");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        List<CartItemRes> cartItems = orderService.handleCheckCartItems(cartItemReqs);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Kiểm tra giỏ hàng thành công");
        response.setData(cartItems);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user/{userId}/create-order")
    public ResponseEntity<ApiResponse<Object>> createOrder(
            @PathVariable Long userId,
            @Valid @RequestBody OrderReq orderReq,
            BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        ApiResponse<Object> response = new ApiResponse<>();
        System.out.println(">>> [INFO] OrderReq: " + orderReq);


        AccountInfoRes accountInfoRes = (AccountInfoRes) SecurityUtils.getCurrentUser();

        if (accountInfoRes.getId() != userId) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setMessage("Bạn không có quyền thực hiện hành động này");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

       

        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("Tạo đơn hàng thành công");
        response.setData(null);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    
}
