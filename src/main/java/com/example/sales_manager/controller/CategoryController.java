package com.example.sales_manager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sales_manager.dto.CategoryDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    
    @GetMapping("/getAllCategories")
    public ResponseEntity<String> getAllCategories() {
        return ResponseEntity.ok("Hello World");
        
    }

    @PostMapping("/addCategory")
    public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(
                    bindingResult.getAllErrors().stream()
                        .map(error -> error.getDefaultMessage()).toList());
            }
            return ResponseEntity.ok("Hello World");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    
}
