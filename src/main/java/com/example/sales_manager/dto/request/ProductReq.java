package com.example.sales_manager.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductReq {
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

    public ProductReq() {

    }

    public ProductReq(String skuCode, List<MultipartFile> productImages, List<MultipartFile> promotionImages,
            List<MultipartFile> descriptionImages, String productName, String description, String brand,
            String countryOfOrigin, List<String> materials, Float originalPrice, Float sellingPrice, Float discount,
            Long stock, String variation1, List<String> options1, String variation2, List<String> options2,
            List<Long> categoryIds, List<SkuReq> skus) {
        this.skuCode = skuCode;
        this.productImages = productImages;
        this.promotionImages = promotionImages;
        this.descriptionImages = descriptionImages;
        this.productName = productName;
        this.description = description;
        this.brand = brand;
        this.countryOfOrigin = countryOfOrigin;
        this.materials = materials;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
        this.stock = stock;
        this.variation1 = variation1;
        this.options1 = options1;
        this.variation2 = variation2;
        this.options2 = options2;
        this.categoryIds = categoryIds;
        this.skus = skus;
    }

    // Getters
    public String getSkuCode() {
        return skuCode;
    }

    public List<MultipartFile> getProductImages() {
        return productImages;
    }

    public List<MultipartFile> getPromotionImages() {
        return promotionImages;
    }

    public List<MultipartFile> getDescriptionImages() {
        return descriptionImages;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public List<String> getMaterials() {
        return materials;
    }

    public Float getOriginalPrice() {
        return originalPrice;
    }

    public Float getSellingPrice() {
        return sellingPrice;
    }

    public Float getDiscount() {
        return discount;
    }

    public Long getStock() {
        return stock;
    }

    public String getVariation1() {
        return variation1;
    }

    public List<String> getOptions1() {
        return options1;
    }

    public String getVariation2() {
        return variation2;
    }

    public List<String> getOptions2() {
        return options2;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public List<SkuReq> getSkus() {
        return skus;
    }

    // Setters
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public void setProductImages(List<MultipartFile> productImages) {
        this.productImages = productImages;
    }

    public void setPromotionImages(List<MultipartFile> promotionImages) {
        this.promotionImages = promotionImages;
    }

    public void setDescriptionImages(List<MultipartFile> descriptionImages) {
        this.descriptionImages = descriptionImages;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public void setMaterials(List<String> materials) {
        this.materials = materials;
    }

    public void setOriginalPrice(Float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public void setSellingPrice(Float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public void setVariation1(String variation1) {
        this.variation1 = variation1;
    }

    public void setOptions1(List<String> options1) {
        this.options1 = options1;
    }

    public void setVariation2(String variation2) {
        this.variation2 = variation2;
    }

    public void setOptions2(List<String> options2) {
        this.options2 = options2;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public void setSkus(List<SkuReq> skus) {
        this.skus = skus;
    }

}