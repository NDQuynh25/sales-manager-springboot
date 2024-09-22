package com.example.sales_manager.controller;

import com.example.sales_manager.dto.request.ReqRoleDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.sales_manager.dto.response.RestResponse;
import com.example.sales_manager.entity.User;
import com.example.sales_manager.service.RoleService;
import com.turkraft.springfilter.boot.Filter;

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

    @GetMapping("/getAll")
    public ResponseEntity<RestResponse<Object>> getAllRoles(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @Filter Specification<User> spec,
            Sort sort) throws Exception {

        if (sort == null) {
            sort = Sort.by(Sort.Order.asc("fullName")); // Sort by 'fullName' in ascending order
        }
        // Create pageable
        Pageable pageable = PageRequest.of(page, size, sort);

        // Create response
        RestResponse<Object> response = new RestResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Get roles successfully");
        response.setData(roleService.handleGetRoles(pageable));
        return ResponseEntity.ok(response);
    }   

    @GetMapping("/getById/{id}")
    public ResponseEntity<RestResponse<Object>> getRoleById(@PathVariable("id") Long id) throws Exception {
        
        // Create response
        RestResponse<Object> response = new RestResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Get role successfully");
        response.setData(roleService.handleGetRoleById(id));
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<RestResponse<Object>> addRole(@RequestBody ReqRoleDto reqRoleDto) throws Exception {

        System.out.println(">>> RoleController.addRole: " + reqRoleDto.getPermissionIds());
        // Create response
        RestResponse<Object> response = new RestResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Create role successfully");
        response.setData(roleService.handleCreateRole(reqRoleDto));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<RestResponse<Object>> updateRole(@PathVariable("id") Long id, @RequestBody ReqRoleDto reqRoleDto) throws Exception {

        // Create response
        RestResponse<Object> response = new RestResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Update role successfully");
        response.setData(roleService.handleUpdateRole(id, reqRoleDto));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<RestResponse<Object>> deleteRole(@PathVariable("id") Long id) throws Exception {

        // Create response
        RestResponse<Object> response = new RestResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Delete role successfully");
        response.setData(roleService.handleDeleteRole(id));
        return ResponseEntity.ok(response);
    }

}
