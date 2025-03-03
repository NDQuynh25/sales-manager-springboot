package com.example.sales_manager.dto.request;

import java.util.ArrayList;
import java.util.List;

public class ReqCategoryDto {
    
    private Long id;

    private String categoryName;

    private List<Long> categoryIds;

    private Integer isActive;

    
    public ReqCategoryDto() {
    }

    public ReqCategoryDto(Long id, String categoryName, List<Long> categoryIds, Integer isActive) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryIds = categoryIds;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {

        this.categoryIds = categoryIds;

    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }



    
}
