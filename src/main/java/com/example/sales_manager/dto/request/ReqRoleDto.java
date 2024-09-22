package com.example.sales_manager.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class ReqRoleDto {

    @JsonProperty("name")
    private String name;
    @JsonProperty("isActive")
    private Integer isActive;
    @JsonProperty("permissionIds")
    private Set<Long> permissionIds = new HashSet<>();

    public ReqRoleDto() {
    }

    public ReqRoleDto(String name, Integer isActive, Set<Long> permissionIds) {
        this.name = name;
        this.isActive = isActive;
        this.permissionIds = permissionIds;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
    public Integer getIsActive() {
        return isActive;
    }
    public void setPermissionIds(Set<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
    public Set<Long> getPermissionIds() {
        return permissionIds;
    }
    
}
