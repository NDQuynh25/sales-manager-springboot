package com.example.sales_manager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.sales_manager.dto.request.ReqCategoryDto;
import com.example.sales_manager.dto.response.RestResponse;
import com.example.sales_manager.entity.Category;
import com.example.sales_manager.service.CategoryService;
import com.turkraft.springfilter.boot.Filter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;


import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    
    @PostMapping("")
    public ResponseEntity<RestResponse<Object>> addCategory(
            @Valid @RequestBody ReqCategoryDto reqcategoryDTO, 
            BindingResult bindingResult
        ) throws Exception{

       
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        System.out.println(">>> reqcategoryDTO: " + reqcategoryDTO);

        RestResponse<Object> response = new RestResponse<>();

        try {
            
            response.setStatus(201);
            response.setError(null);
            response.setMessage("Create category successfully");
            response.setData(categoryService.handleCreateCategory(reqcategoryDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            response.setStatus(500);
            response.setError("Internal Server Error");
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("")
    public ResponseEntity<RestResponse<Object>> getCategories(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) Long id,
            @Filter Specification<Category> spec,
            Sort sort
    ) throws Exception {

        RestResponse<Object> response = new RestResponse<>();

        try {
            // if (id != null) {
            //     response.setStatus(200);
            //     response.setError(null);
            //     response.setMessage("Get category successfully");
            //     response.setData(categoryService.handleGetCategoryById(id));
            //     return ResponseEntity.status(HttpStatus.OK).body(response);
            // }
            Pageable pageable = PageRequest.of(page, size, sort);

            response.setStatus(200);
            response.setError(null);
            response.setMessage("Get all categories successfully");
            response.setData(categoryService.handleGetCategories(spec, pageable));
            return ResponseEntity.status(HttpStatus.OK).body(response);
            


        } catch (Exception e) {
            response.setStatus(500);
            response.setError("Internal Server Error");
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    
}
