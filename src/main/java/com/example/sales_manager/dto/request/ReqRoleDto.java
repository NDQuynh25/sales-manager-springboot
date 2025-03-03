package com.example.sales_manager.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@GroupSequence({ReqCreateUserDto.class, ReqCreateUserDto.Check1.class, ReqCreateUserDto.Check2.class}) //Check order, if check1 fails, there is no need to check check2
public class ReqRoleDto {


    @JsonProperty("roleName")
    @NotBlank(message = "Role name cannot be blank", groups = Check1.class)
    private String roleName;
    @JsonProperty("isActive")
    @NotNull(message = "Status cannot be null", groups = Check1.class)
    @Max(value = 1, message = "Is active must be 0 or 1", groups = Check2.class)
    @Min(value = 0, message = "Is active must be 0 or 1", groups = Check2.class)

    private Integer isActive;
    @JsonProperty("permissionIds")
    private Set<Long> permissionIds = new HashSet<>();

    public ReqRoleDto() {
    }

    public ReqRoleDto(String roleName, Integer isActive, Set<Long> permissionIds) {
        this.roleName = roleName;
        this.isActive = isActive;
        this.permissionIds = permissionIds;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public String getRoleName() {
        return roleName;
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

    public interface Check1 {
    }
    public interface Check2 {
    }
    
}
