package com.example.sales_manager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {
        
    @GetMapping("/getAllPermissions")
    public ResponseEntity<<RestResponse<Object>> getAllPermissions() {
        
        
    }

    
    
}
