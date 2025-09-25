package com.example.sales_manager.dto.response;

public class SummaryCategoryRes {
    private Long id;

    private String categoryName;

    private Integer isActive;

    public SummaryCategoryRes() {
    }

    public SummaryCategoryRes(Long id, String categoryName, Integer isActive) {
        this.id = id;
        this.categoryName = categoryName;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

}
