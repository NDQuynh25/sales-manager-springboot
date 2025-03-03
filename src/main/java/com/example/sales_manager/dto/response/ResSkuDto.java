package com.example.sales_manager.dto.response;

public class ResSkuDto {
    private Long id;

    private String option1;

    private String option2;

    private Float price;

    private Integer stock;

    private Float discount;

    private Integer isActive;

    public ResSkuDto() {
    }

    public ResSkuDto(
            Long id,
            String option1,
            String option2,
            Float price,
            Integer stock,
            Float discount,
            Integer isActive
    ) {
        this.id = id;
        this.option1 = option1;
        this.option2 = option2;
        this.price = price;
        this.stock = stock;
        this.discount = discount;
        this.isActive = isActive;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOption1() {
        return this.option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return this.option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getStock() {
        return this.stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Float getDiscount() {
        return this.discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Integer getIsActive() {
        return this.isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    
}
