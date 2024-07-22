package com.example.sales_manager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import com.example.sales_manager.dto.response.ResPermissionDto;
import com.example.sales_manager.dto.response.RestResponse;
import com.example.sales_manager.entity.Permission;
import com.example.sales_manager.entity.Role;
import com.example.sales_manager.entity.User;
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
    public ResponseEntity<RestResponse<Object>> getAll(
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
        response.setStatus(200);
        response.setMessage("Success");
        response.setData(permissionService.handleGetPermissions(pageable));

        return ResponseEntity.ok(response);
    
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<RestResponse<Object>> getById(@PathVariable("id") Long id) throws Exception {
        
        RestResponse<Object> response = new RestResponse<>();
        response.setStatus(200);
        response.setMessage("Success");
        response.setData(permissionService.handleGetPermissionById(id));

        return ResponseEntity.ok(response);
    }

    
    
}
