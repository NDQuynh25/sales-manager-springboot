package com.example.sales_manager.dto.response;


import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResRoleDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("permissions")
    private Set<ResPermissionDto> permissions = new HashSet<>();

    

    public ResRoleDto() {
    }

    public ResRoleDto(Long id, String name, Set<ResPermissionDto> permissions) {
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
    public void setPermissions(Set<ResPermissionDto> permissions) {
        this.permissions = permissions;
    }
    public Set<ResPermissionDto> getPermissions() {
        return permissions;
    }

  
    
    
}
