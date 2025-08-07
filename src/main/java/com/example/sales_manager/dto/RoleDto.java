package com.example.sales_manager.dto;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;



public class RoleDto {
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("roleName")
    private String roleName;

    @JsonProperty("permissions")
    private Set<PermissionDto> permissions = new HashSet<>();

    public RoleDto() {
    }

    public RoleDto(Long id, String roleName, Set<PermissionDto> permissions) {
        this.id = id;
        this.roleName = roleName;
        this.permissions = permissions;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public String getRoleName() {
        return roleName;
    }

    public void setPermissions(Set<PermissionDto> permissions) {
        this.permissions = permissions;
    }
    public Set<PermissionDto> getPermissions() {
        return permissions;
    }


    
}
