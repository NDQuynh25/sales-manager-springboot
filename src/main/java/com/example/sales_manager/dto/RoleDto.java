package com.example.sales_manager.dto;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;



public class RoleDto {
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("name")
    private String name;

    @JsonProperty("permissions")
    private Set<PermissionDto> permissions = new HashSet<>();

    public RoleDto() {
    }

    public RoleDto(Long id, String name, Set<PermissionDto> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
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

    public void setPermissions(Set<PermissionDto> permissions) {
        this.permissions = permissions;
    }
    public Set<PermissionDto> getPermissions() {
        return permissions;
    }


    
}
