package com.example.sales_manager.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CartItemRes {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Product {
        private Long id;
        private String productName;
        private String variation1;
        private String variation2;
        private List<String> options1;
        private List<String> options2;
        private List<String> promotionImageURLs;
        private Long isActive;
        private List<SkuRes> skus;
    }
    private Long id;
    private Product product;
    private SkuRes sku;
    private Integer quantity;
    private Integer isActive;  
}
