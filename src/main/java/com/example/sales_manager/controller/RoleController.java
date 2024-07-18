package com.example.sales_manager.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.sales_manager.dto.request.ReqRoleDto;
import com.example.sales_manager.entity.User;
import com.example.sales_manager.exception.RestResponse;
import com.example.sales_manager.service.RoleService;
import com.turkraft.springfilter.boot.Filter;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import jakarta.validation.Valid;
import org.hibernate.query.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/getRoles")
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

    @GetMapping("/getRoleById/{id}")
    public ResponseEntity<RestResponse<Object>> getRoleById(@PathVariable("id") Long id) throws Exception {
        
        // Create response
        RestResponse<Object> response = new RestResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Get role successfully");
        response.setData(roleService.handleGetRoleById(id));
        return ResponseEntity.ok(response);
    }
}
