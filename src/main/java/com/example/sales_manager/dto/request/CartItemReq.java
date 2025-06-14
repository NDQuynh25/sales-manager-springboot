package com.example.sales_manager.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemReq {
    private Long cartId;
    private Long skuId;
    private Long productId;
    private Integer quantity;    
}
