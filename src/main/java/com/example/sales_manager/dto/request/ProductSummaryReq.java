package com.example.sales_manager.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductSummaryReq {
    private String skuCode;
    private List<MultipartFile> productImages;
    private List<MultipartFile> promotionImages;
    private List<MultipartFile> descriptionImages;

    private String productName;
    private String description;
    private String brand;
    private String countryOfOrigin;
    private List<String> materials;
    private Float originalPrice;
    private Float sellingPrice;
    private Float discount;
    private Long stock;
    private String variation1;
    private List<String> options1;
    private String variation2;
    private List<String> options2;
    private List<Long> categoryIds;
    private List<SkuReq> skus;

}