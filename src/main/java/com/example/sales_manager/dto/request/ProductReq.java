package com.example.sales_manager.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class ProductReq {

    public static class Sku {

        @JsonProperty("imageURL")
        private String imageURL;

        @JsonProperty("option1")
        private String option1;

        @JsonProperty("option2")
        private String option2;

        @JsonProperty("price")
        private Float price;

        @JsonProperty("stock")
        private Integer stock;

        @JsonProperty("discount")
        private Float discount;

        public Sku() {
        }

        public Sku(String imageURL, String option1, String option2, Float price, Integer stock, Float discount) {
            this.imageURL = imageURL;
            this.option1 = option1;
            this.option2 = option2;
            this.price = price;
            this.stock = stock;
            this.discount = discount;
        }

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
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

        public Float getPrice() {
            return price;
        }

        public void setPrice(Float price) {
            this.price = price;
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
    }

    @NotEmpty(message = "Image URLs cannot be empty!")
    @JsonProperty("imageURLs")
    private List<String> imageURLs;

    @NotBlank(message = "Promotion Image URL cannot be blank!")
    @JsonProperty("promotionImageURL")
    private String promotionImageURL;

    @NotBlank(message = "Product name cannot be blank!")
    @JsonProperty("productName")
    private String productName;

    @NotNull(message = "Category ID cannot be null!")
    @JsonProperty("categoryId")
    private Long categoryId;

    @NotBlank(message = "Description cannot be blank!")
    @JsonProperty("description")
    private String description;

    @NotBlank(message = "Brand cannot be blank!")
    @JsonProperty("brand")
    private String brand;

    @NotEmpty(message = "Materials cannot be empty!")
    @JsonProperty("materials")
    private List<String> materials;

    @NotBlank(message = "Country of origin cannot be blank!")
    @JsonProperty("countryOfOrigin")
    private String countryOfOrigin;

    @JsonProperty("price")
    private Float price;

    @JsonProperty("discount")
    private Float discount;

    @JsonProperty("stock")
    private Long stock;

    @JsonProperty("variation1")
    private String variation1;

    @JsonProperty("options1")
    private List<String> options1;

    @JsonProperty("variation2")
    private String variation2;

    @JsonProperty("options2")
    private List<String> options2;

    @JsonProperty("skus")
    private List<Sku> skus;

    @JsonProperty("isActive")
    private Integer isActive;

    public ProductReq() {
    }

    public ProductReq(

            List<String> imageURLs,
            String promotionImageURL,
            String productName,
            Long categoryId,
            String description,
            String brand,
            List<String> materials,
            String countryOfOrigin,
            Float price,
            Float discount,
            Long stock,
            String variation1,
            List<String> options1,
            String variation2,
            List<String> options2,
            List<Sku> skus,
            Integer isActive) {
        this.imageURLs = imageURLs;
        this.promotionImageURL = promotionImageURL;
        this.productName = productName;
        this.categoryId = categoryId;
        this.description = description;
        this.brand = brand;
        this.materials = materials;
        this.countryOfOrigin = countryOfOrigin;
        this.price = price;
        this.discount = discount;
        this.stock = stock;
        this.variation1 = variation1;
        this.options1 = options1;
        this.variation2 = variation2;
        this.options2 = options2;
        this.skus = skus;
        this.isActive = isActive;

    }

    public List<String> getImageURLs() {
        return imageURLs;
    }

    public void setImageURLs(List<String> imageURLs) {
        this.imageURLs = imageURLs;
    }

    public String getPromotionImageURL() {
        return promotionImageURL;
    }

    public void setPromotionImageURL(String promotionImageURL) {
        this.promotionImageURL = promotionImageURL;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<String> getMaterials() {
        return materials;
    }

    public void setMaterials(List<String> materials) {
        this.materials = materials;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
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

    public void setOption2(List<String> options2) {
        this.options2 = options2;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

}
