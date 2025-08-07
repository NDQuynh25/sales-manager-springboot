package com.example.sales_manager.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailRes {

    private Long id;

    private List<String> productImageURLs;

    private List<String> promotionImageURLs;

    private String productName;

    private String description;

    private String brand;

    private String countryOfOrigin;

    private List<String> materials;

    // Information to be calculated
    private SkuRes skuDisplay;

    private Long totalQuantitySold;

    private Long totalStock;

    //////////////////////////////////////
    private String variation1;

    private List<String> options1;

    private String variation2;

    private List<String> options2;

    private List<Long> categoryIds;

    private List<SkuRes> skus;

    private Integer isActive;

    private Instant createdAt;

    private Instant updatedAt;

    private String createdBy;

    private String updatedBy;


    // 
    

}
