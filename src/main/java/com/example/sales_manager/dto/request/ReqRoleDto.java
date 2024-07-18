package com.example.sales_manager.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReqRoleDto {

    @JsonProperty("name")
    private String name;

    public ReqRoleDto() {
    }

    public ReqRoleDto(String name) {
        this.name = name;
    
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    
}
