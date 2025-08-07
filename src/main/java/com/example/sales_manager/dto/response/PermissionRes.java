package com.example.sales_manager.dto.response;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PermissionRes {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("permissionName")
    private String permissionName;

    @JsonProperty("apiAccess")
    private String apiAccess;

    @JsonProperty("method")
    private String method;

    @JsonProperty("description")
    private String description;

    @JsonProperty("isActive")
    private Integer isActive;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;

    @JsonProperty("createdAt")
    private Instant createdAt;

    @JsonProperty("updatedAt")
    private Instant updatedAt;

    public PermissionRes() {
    }

    public PermissionRes(Long id, String permissionName, String apiAccess, String method, String description,
            Integer isActive, String createdBy, String updatedBy, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.permissionName = permissionName;
        this.apiAccess = apiAccess;
        this.method = method;
        this.description = description;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
