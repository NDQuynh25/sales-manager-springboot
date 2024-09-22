package com.example.sales_manager.dto.response;

import java.time.Instant;

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





    public ResPermissionDto() {
    }

    public ResPermissionDto(Long id, String name, String apiAccess, String method, Integer isActive, String createdBy, String updatedBy, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.apiAccess = apiAccess;
        this.method = method;
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
