package com.example.sales_manager.dto.request;


public class ReqCartDto {
    private Long userId;


    public ReqCartDto() {
    }

    public ReqCartDto(Long userId) {
        this.userId = userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
