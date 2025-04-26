package com.example.sales_manager.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductReq {
    private String skuCode;
    private List<MultipartFile> productImages; // Thay thế List<String> imageURLs
    private List<MultipartFile> promotionImages; // Thay thế List<String> promotionImageURLs
    private String productName;
    private String description;

    ProductReq() {

    }

    ProductReq(
            String skuCode,
            List<MultipartFile> productImages,
            List<MultipartFile> promotionImages,
            String productName,
            String description) {
        this.skuCode = skuCode;
        this.productImages = productImages;
        this.promotionImages = promotionImages;
        this.productName = productName;
        this.description = description;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public List<MultipartFile> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<MultipartFile> productImages) {
        this.productImages = productImages;
    }

    public List<MultipartFile> getPromotionImages() {
        return promotionImages;

    }

    public void setPromotionImages(List<MultipartFile> promotionImages) {
        this.promotionImages = promotionImages;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}