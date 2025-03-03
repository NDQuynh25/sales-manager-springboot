package com.example.sales_manager.controller;

import com.example.sales_manager.dto.request.ReqPermissionDto;
import com.example.sales_manager.entity.Permission;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import com.example.sales_manager.dto.response.RestResponse;
import com.example.sales_manager.service.PermissionService;
import com.turkraft.springfilter.boot.Filter;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
        
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RestResponse<Object>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @Filter Specification<Permission> spec,
            Sort sort) throws Exception {

        if (sort == null) {
            sort = Sort.by(Sort.Order.asc("permissionName"));
        }
        // Create pageable
        Pageable pageable = PageRequest.of(page, size, sort);

        // Create response
        RestResponse<Object> response = new RestResponse<>();
        response.setStatus(200);
        response.setMessage("Get permissions successfully");
        response.setData(permissionService.handleGetPermissions(pageable));

        return ResponseEntity.ok(response);
    
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RestResponse<Object>> getById(@PathVariable("id") Long id) throws Exception {
        
        RestResponse<Object> response = new RestResponse<>();
        response.setStatus(200);
        response.setMessage("Get permission successfully");
        response.setData(permissionService.handleGetPermissionById(id));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RestResponse<Object>> create(
            @Valid @RequestBody ReqPermissionDto reqPermissionDto,
            BindingResult bindingResult) throws Exception {


        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        RestResponse<Object> response = new RestResponse<>();
        response.setStatus(201);
        response.setMessage("Create permission successfully");
        response.setData(permissionService.mapPermissionToResPermissionDto(permissionService.handleCreatePermission(reqPermissionDto)));

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RestResponse<Object>> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody ReqPermissionDto reqPermissionDto,
            BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        RestResponse<Object> response = new RestResponse<>();
        response.setStatus(200);
        response.setMessage("Update permission successfully");
        response.setData(permissionService.mapPermissionToResPermissionDto(permissionService.handleUpdatePermission(id, reqPermissionDto)));

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RestResponse<Object>> delete(@PathVariable("id") Long id) throws Exception {

        RestResponse<Object> response = new RestResponse<>();
        response.setStatus(200);
        response.setMessage("Delete permission successfully");
        response.setData(permissionService.handleDeletePermission(id));

        return ResponseEntity.ok(response);
    }

    
    
}
