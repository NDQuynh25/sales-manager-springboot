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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<Object>> getCartByUserId(
            @PathVariable Long userId,
            @Filter Specification<CartItem> spec,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Sort sort
    ) throws Exception {
        if (sort == null) {
            sort = Sort.by(Sort.Direction.DESC, "updatedAt");
        }
        
        Pageable pageable = PageRequest.of(page, size, sort);
        
        ApiResponse<Object> response = new ApiResponse<>();
        response.setStatus(200);
        response.setError(null);
        response.setMessage("Get cart by user ID successfully");
        response.setData(cartService.getCartByUserId(userId, spec, pageable));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/user/{userId}/item/{cartItemId}")
    public ResponseEntity<ApiResponse<Object>> updateCartItem(
            @PathVariable Long userId,
            @PathVariable Long cartItemId,
            @RequestBody CartItemReq cartItemReq,
            BindingResult bindingResult
    ) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        ApiResponse<Object> response = new ApiResponse<>();
        response.setStatus(200);
        response.setError(null);
        response.setMessage("Update cart item successfully");
        response.setData(cartService.updateCartItem(userId, cartItemId, cartItemReq));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/user/{userId}/item/{cartItemId}")
    public ResponseEntity<ApiResponse<Object>> deleteCartItem(
            @PathVariable Long userId,
            @PathVariable Long cartItemId
    ) throws Exception {
        ApiResponse<Object> response = new ApiResponse<>();
        response.setStatus(200);
        response.setError(null);
        response.setMessage("Delete cart item successfully");
        response.setData(cartService.deleteCartItem(userId, cartItemId));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
