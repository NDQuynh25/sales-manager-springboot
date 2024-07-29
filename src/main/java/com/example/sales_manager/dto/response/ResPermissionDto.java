package com.example.sales_manager.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResPermissionDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("api_access")
    private String apiAccess;

    @JsonProperty("method")
    private String method;


    public ResPermissionDto() {
    }

    public ResPermissionDto(Long id, String name, String apiAccess, String method) {
        this.id = id;
        this.name = name;
        this.apiAccess = apiAccess;
        this.method = method;
     
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setApiAccess(String apiAccess) {
        this.apiAccess = apiAccess;
    }

    public String getApiAccess() {
        return apiAccess;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    
}
