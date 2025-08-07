package com.example.sales_manager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.sales_manager.dto.request.CategoryReq;
import com.example.sales_manager.dto.response.ApiResponse;
import com.example.sales_manager.entity.Category;
import com.example.sales_manager.service.CategoryService;
import com.turkraft.springfilter.boot.Filter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Object>> addCategory(
            @Valid @RequestBody CategoryReq CategoryReq,
            BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        System.out.println(">>> CategoryReq: " + CategoryReq);

        ApiResponse<Object> response = new ApiResponse<>();
        response.setStatus(201);
        response.setError(null);
        response.setMessage("Create category successfully");
        response.setData(
                categoryService.mapCategoryToCategoryRes(categoryService.handleCreateCategory(CategoryReq)));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryReq CategoryReq,
            BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        ApiResponse<Object> response = new ApiResponse<>();
        response.setStatus(200);
        response.setError(null);
        response.setMessage("Update category successfully");
        response.setData(categoryService.mapCategoryToCategoryRes(categoryService.handleUpdateCategory(id, CategoryReq)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteCategory(@PathVariable Long id) throws Exception {

        ApiResponse<Object> response = new ApiResponse<>();
        response.setStatus(200);
        response.setError(null);
        response.setMessage("Delete category successfully");
        response.setData(categoryService.handleDeleteCategory(id));
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<Object>> getCategories(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "false") Boolean isSummary,
            @Filter Specification<Category> spec,
            Sort sort) throws Exception {

        ApiResponse<Object> response = new ApiResponse<>();
        Pageable pageable = PageRequest.of(page, size, sort);

        response.setStatus(200);
        response.setError(null);
        response.setMessage("Get all categories successfully");
        response.setData(categoryService.handleGetCategories(spec, pageable, isSummary));
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getCategoryById(@PathVariable Long id) throws Exception {

        ApiResponse<Object> response = new ApiResponse<>();

        response.setStatus(200);
        response.setError(null);
        response.setMessage("Get category by id successfully");
        response.setData(categoryService.mapCategoryToCategoryRes(categoryService.handleGetCategoryById(id)));
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}
