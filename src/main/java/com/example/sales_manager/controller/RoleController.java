package com.example.sales_manager.controller;

import com.example.sales_manager.dto.request.RoleReq;
import com.example.sales_manager.entity.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.sales_manager.dto.response.ApiResponse;
import com.example.sales_manager.service.RoleService;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import org.springframework.validation.BindException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<Object>> getAllRoles(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) Long id,
            @Filter Specification<Role> spec,
            Sort sort) throws Exception {

        // if (sort == null) {
        // sort = Sort.by(Sort.Order.asc("roleName"));
        // }

        if (id != null) {
            // Create response
            ApiResponse<Object> response = new ApiResponse<>();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Get role successfully");
            response.setData(roleService.mapRoleToRoleRes(roleService.handleGetRoleById(id)));
            return ResponseEntity.ok(response);
        }

        // Create pageable
        Pageable pageable = PageRequest.of(page, size, sort);

        // Create response
        ApiResponse<Object> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Get roles successfully");
        response.setData(roleService.handleGetRoles(spec, pageable));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Object>> addRole(@RequestBody RoleReq RoleReq) throws Exception {

        System.out.println(">>> RoleController.addRole: " + RoleReq.getPermissionIds());
        // Create response
        ApiResponse<Object> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Create role successfully");
        response.setData(roleService.mapRoleToRoleRes(roleService.handleCreateRole(RoleReq)));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Object>> updateRole(@PathVariable("id") Long id,
            @Valid @RequestBody RoleReq RoleReq, BindException bindException) throws Exception {

        if (bindException.hasErrors()) {
            throw new BindException(bindException);
        }
        // Create response
        ApiResponse<Object> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Update role successfully");
        response.setData(roleService.mapRoleToRoleDto(roleService.handleUpdateRole(id, RoleReq)));
        return ResponseEntity.ok(response);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteRole(@PathVariable("id") Long id) throws Exception {

        // Create response
        ApiResponse<Object> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Delete role successfully");
        response.setData(roleService.handleDeleteRole(id));
        return ResponseEntity.ok(response);
    }

}
