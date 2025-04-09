package com.example.sales_manager.dto.request;

public class CartReq {
    private Long userId;

    public CartReq() {
    }

    public CartReq(Long userId) {
        this.userId = userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
