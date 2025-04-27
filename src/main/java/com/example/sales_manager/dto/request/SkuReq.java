package com.example.sales_manager.dto.request;

public class SkuReq {

    // private MultipartFile imageFile;
    private Long id;

    private String skuCode;

    private String option1;

    private String option2;

    private Float originalPrice;

    private Float sellingPrice;

    private Integer stock;

    private Float discount;

    private Integer isActive;

    public SkuReq() {
    }

    public SkuReq(Long id, String skuCode, String option1, String option2, Float originalPrice, Float sellingPrice,
            Integer stock, Float discount, Integer isActive) {
        this.id = id;
        this.skuCode = skuCode;
        this.option1 = option1;
        this.option2 = option2;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.stock = stock;
        this.discount = discount;
        this.isActive = isActive;
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

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
}
