package com.example.sales_manager.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PermissionReq {
    @JsonProperty("permissionName")
    @NotBlank(message = "Permission name cannot be blank")
    private String permissionName;

    @JsonProperty("apiAccess")
    @NotBlank(message = "Api access cannot be blank")
    private String apiAccess;

    @JsonProperty("method")
    @NotBlank(message = "Method cannot be blank")
    private String method;

    @JsonProperty("description")
    private String description;

    @JsonProperty("isActive")
    @NotNull(message = "Status cannot be null")
    @Min(value = 0, message = "Status must be 0 or 1")
    @Max(value = 1, message = "Status must be 0 or 1")
    private Integer isActive;

    public PermissionReq() {
    }

    public PermissionReq(String permissionName, String apiAccess, String method, String description, Integer isActive) {
        this.permissionName = permissionName;
        this.apiAccess = apiAccess;
        this.method = method;
        this.description = description;
        this.isActive = isActive;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getApiAccess() {
        return apiAccess;
    }

    public void setApiAccess(String apiAccess) {
        this.apiAccess = apiAccess;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

}
