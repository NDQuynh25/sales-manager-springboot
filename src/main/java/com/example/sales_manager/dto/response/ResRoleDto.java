package com.example.sales_manager.dto.response;

import java.util.HashSet;
import com.example.sales_manager.entity.Permission;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class ResRoleDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("permissions")
    private Set<Permission> permissions = new HashSet<>();

    public ResRoleDto() {
    }

    public ResRoleDto(Long id, String name, Set<Permission> permissions) {
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

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
    
}
