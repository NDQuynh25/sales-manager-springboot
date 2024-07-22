package com.example.sales_manager.dto.response;

import java.util.Set;
import java.util.HashSet;
import com.example.sales_manager.entity.Role;
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

    @JsonProperty("description")
    private String description;

    @JsonProperty("roles")
    private Set<Role> roles = new HashSet<>();

    public ResPermissionDto() {
    }

    public ResPermissionDto(Long id, String name, String apiAccess, String method, String description, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.apiAccess = apiAccess;
        this.method = method;
        this.description = description;
        this.roles = roles;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }
    
}
