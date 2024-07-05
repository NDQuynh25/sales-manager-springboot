package com.example.sales_manager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResLoginDto {
    
    @JsonProperty("access_token")
    String accessToken;

    public ResLoginDto() {
    }
    public ResLoginDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getAccessToken() {
        return accessToken;
    }
    
}
