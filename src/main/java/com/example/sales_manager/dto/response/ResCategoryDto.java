package com.example.sales_manager.dto.response;

import java.time.Instant;
import java.util.List;
import com.example.sales_manager.entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ResCategoryDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("categoryName")
    private String categoryName;

    @JsonProperty("productQuantity")
    private Long productQuantity;

    @JsonProperty("subCategories")
    private List<Category> subCategories;

    @JsonProperty("isActive")
    private Integer isActive;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;

    @JsonProperty("createdAt")
    private Instant createdAt;

    @JsonProperty("updatedAt")
    private Instant updatedAt;


    public ResCategoryDto(
            Long id, 
            String categoryName, 
            Long productQuantity, 
            List<Category> subCategories,
            Integer isActive,
            String createdBy,
            String updatedBy,
            Instant createdAt,
            Instant updatedAt
        ) {
        
        this.id = id;
        this.categoryName = categoryName;
        this.productQuantity = productQuantity;
        this.subCategories = subCategories;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public Long getProductQuantity() {
        return productQuantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public void setProductQuantity(Long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
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
