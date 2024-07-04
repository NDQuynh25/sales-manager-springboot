package com.example.sales_manager.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;


public class CategoryDTO {
    private Long id;

    @NotNull(message = "Category's name cannot be null!")
    @NotEmpty(message = "Category's name cannot be empty!")
    private String name;

    private Integer is_active;

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String name, Integer is_active) {
        this.id = id;
        this.name = name;
        this.is_active = is_active;
    }
    public Long getID() {
        return this.id;
    }
    public void setID(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsActive() {
        return this.is_active;
    }
    public void setIsActive(Integer is_active) {
        this.is_active = is_active;
    }
    
    public String toString() {
        return "CategoryDTO{id=" + id + ", name=" + name + ", is_active=" + is_active + "}";
    }
}
