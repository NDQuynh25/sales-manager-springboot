package com.example.sales_manager.dto.response;

import java.time.Instant;
import java.util.List;

public class SummaryProductRes {
    
    private Long id;

    private List<String> imageURLs;

    private List<String> promotionImageURLs;

    private String productName;

    private Float sellingPrice;

    private Float discount;

    private Long quantitySold;

    private Integer isActive;

    private Instant createdAt;

    private Instant updatedAt;

    public SummaryProductRes() {
    }

    public SummaryProductRes(
            Long id, 
            List<String> imageURLs, 
            List<String> promotionImageURLs,
            String productName, 
            Float sellingPrice, 
            Float discount,
            Long quantitySold,
            Integer isActive,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.imageURLs = imageURLs;
        this.promotionImageURLs = promotionImageURLs;
        this.productName = productName;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
        this.quantitySold = quantitySold;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getImageURLs() {
        return imageURLs;
    }

    public void setImageURLs(List<String> imageURLs) {
        this.imageURLs = imageURLs;
    }

    public List<String> getPromotionImageURLs() {
        return promotionImageURLs;
    }

    public void setPromotionImageURLs(List<String> promotionImageURLs) {
        this.promotionImageURLs = promotionImageURLs;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Long getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Long quantitySold) {
        this.quantitySold = quantitySold;
    }
    
    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    
}
