package com.example.sales_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.example.sales_manager.dto.response.ApiResponse;
import com.example.sales_manager.service.CartService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;




@RestController
@RequestMapping("/api/v1/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<Object>> getCart(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "userId") Long userId,
            @RequestParam(required = false, defaultValue = "asc") String sort
    ){

        ApiResponse<Object> response = new ApiResponse<>();
        response.setStatus(200);
        response.setError(null);
        response.setMessage("Get cart successfully");
        response.setData(cartService.handleGetCarts(userId, page, size, sort));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    


}
