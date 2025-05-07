package com.example.sales_manager.dto.response;

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

    private String skuCode;

    private List<String> productImageURLs;

    private List<String> promotionImageURLs;

    private String productName;

    private String description;

    private String brand;

    private String countryOfOrigin;

    private List<String> materials;

    private Float originalPrice;

    private Float sellingPrice;

    private Float discount;

    private Long quantitySold;

    private Long stock;

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

}
