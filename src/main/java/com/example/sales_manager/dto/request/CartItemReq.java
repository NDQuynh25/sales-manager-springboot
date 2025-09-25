package com.example.sales_manager.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemReq {
    private Long id; 
    private Long cartId;
    private Long skuId;
    private String option1;
    private String option2;
    private Long productId;
    private BigDecimal originalPriceCurrent;
    private BigDecimal sellingPriceCurrent;
    private Integer quantity;    
}
