package com.example.sales_manager.controller;


import org.springframework.web.bind.annotation.RestController;
import com.example.sales_manager.dto.request.CartItemReq;
import com.example.sales_manager.dto.response.ApiResponse;
import com.example.sales_manager.entity.CartItem;
import com.example.sales_manager.mapper.CartMapper;
import com.example.sales_manager.service.CartService;
import com.turkraft.springfilter.boot.Filter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.data.domain.Sort;




@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    private final CartMapper cartMapper;

    public CartController(CartService cartService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

   
    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<Object>> addItemToCart(
            @PathVariable Long userId,
            @RequestBody CartItemReq cartItemReq,
            BindingResult bindingResult
    ) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        ApiResponse<Object> response = new ApiResponse<>();
        response.setStatus(200);
        response.setError(null);
        response.setMessage("Add item to cart successfully");
        response.setData(
                cartService.addItemToCart(userId, cartItemReq)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
