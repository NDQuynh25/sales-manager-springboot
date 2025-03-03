package com.example.sales_manager.dto.response;

public class ResProductDto {
    private Long id;

    public ResProductDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
