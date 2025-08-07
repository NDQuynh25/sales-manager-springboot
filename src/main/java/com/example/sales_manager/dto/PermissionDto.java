package com.example.sales_manager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PermissionDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("permissionName")
    private String permissionName;

    @JsonProperty("apiAccess")
    private String apiAccess;

    @JsonProperty("method")
    private String method;

    public PermissionDto() {
    }

    public PermissionDto(Long id, String permissionName, String apiAccess, String method) {
        this.id = id;
        this.permissionName = permissionName;
        this.apiAccess = apiAccess;
        this.method = method;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
    public String getPermissionName() {
        return permissionName;
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
