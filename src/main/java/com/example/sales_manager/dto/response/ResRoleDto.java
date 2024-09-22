package com.example.sales_manager.dto.response;


import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ResRoleDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("isActive")
    private Integer isActive;

    @JsonProperty("permissions")
    private Set<ResPermissionDto> permissions = new HashSet<>();

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;

    @JsonProperty("createdAt")
    private Instant createdAt;

    @JsonProperty("updatedAt")
    private Instant updatedAt;

    

    public ResRoleDto() {
    }

    public ResRoleDto(Long id, String name, Integer isActive, Set<ResPermissionDto> permissions, String createdBy, String updatedBy, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.permissions = permissions;
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

    public Integer getIsActive() {
        return isActive;
    }
    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public void setPermissions(Set<ResPermissionDto> permissions) {
        this.permissions = permissions;
    }
    public Set<ResPermissionDto> getPermissions() {
        return permissions;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getCreatedBy() {
        return createdBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }

  
    
    
}
