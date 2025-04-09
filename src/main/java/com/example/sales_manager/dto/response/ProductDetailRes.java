package com.example.sales_manager.dto.response;

import java.time.Instant;
import java.util.List;

public class ProductDetailRes {

    private Long id;

    private String skuCode;

    private List<String> imageURLs;

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

    

    public ProductDetailRes() {
    }

    public ProductDetailRes(
            Long id,
            String skuCode,
            List<String> imageURLs,
            List<String> promotionImageURLs,
            String productName,
            String description,
            String brand,
            String countryOfOrigin,
            List<String> materials,
            Float originalPrice,
            Float sellingPrice,
            Float discount,
            Long quantitySold,
            Long stock,
            String variation1,
            List<String> options1,
            String variation2,
            List<String> options2,
            List<Long> categoryIds,
            List<SkuRes> skus,
            Integer isActive,
            Instant createdAt,
            Instant updatedAt, 
            String createdBy,
            String updatedBy

    ) {
        this.id = id;
        this.skuCode = skuCode;
        this.imageURLs = imageURLs;
        this.promotionImageURLs = promotionImageURLs;
        this.productName = productName;
        this.description = description;
        this.brand = brand;
        this.countryOfOrigin = countryOfOrigin;
        this.materials = materials;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
        this.quantitySold = quantitySold;
        this.stock = stock;
        this.variation1 = variation1;
        this.options1 = options1;
        this.variation2 = variation2;
        this.options2 = options2;
        this.categoryIds = categoryIds;
        this.skus = skus;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public List<String> getMaterials() {
        return materials;
    }

    public void setMaterials(List<String> materials) {
        this.materials = materials;
    }

    public Float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Float originalPrice) {
        this.originalPrice = originalPrice;
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

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getVariation1() {
        return variation1;
    }

    public void setVariation1(String variation1) {
        this.variation1 = variation1;
    }

    public List<String> getOptions1() {
        return options1;
    }

    public void setOptions1(List<String> options1) {
        this.options1 = options1;
    }

    public String getVariation2() {
        return variation2;
    }

    public void setVariation2(String variation2) {
        this.variation2 = variation2;
    }

    public List<String> getOptions2() {
        return options2;
    }

    public void setOptions2(List<String> options2) {
        this.options2 = options2;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public List<SkuRes> getSkus() {
        return skus;
    }

    public void setSkus(List<SkuRes> skus) {
        this.skus = skus;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsActive() {
        return isActive;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }


}
