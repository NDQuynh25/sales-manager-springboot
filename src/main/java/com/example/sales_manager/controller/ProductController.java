package com.example.sales_manager.controller;

import com.example.sales_manager.dto.request.ProductReq;
import com.example.sales_manager.dto.response.ApiResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.validation.BindException;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import com.example.sales_manager.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkraft.springfilter.boot.Filter;
import com.example.sales_manager.entity.Product;
import org.springframework.http.MediaType;
import com.example.sales_manager.mapper.ProductMapper;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Set;

@Controller
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;

    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Object>> addProduct(
            @RequestPart(required = false, value = "productData") String productDataJson,
            @RequestPart(required = false, value = "productImages") List<MultipartFile> productImages,
            @RequestPart(required = false, value = "promotionImages") List<MultipartFile> promotionImages,
            @RequestPart(required = false, value = "descriptionImages") List<MultipartFile> descriptionImages,
            BindingResult bindingResult) throws Exception {

        if (productDataJson == null || productDataJson.isEmpty()) {
            throw new IllegalArgumentException("Dữ liệu sản phẩm không được để trống");
        }

        System.out.println("[INFO] Received product data: " + productDataJson);

        ProductReq productReq;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            productReq = objectMapper.readValue(productDataJson, ProductReq.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Dữ liệu JSON không hợp lệ: " + e.getMessage());
        }

        // Set các danh sách ảnh
        productReq.setProductImages(productImages);
        productReq.setPromotionImages(promotionImages);
        productReq.setDescriptionImages(descriptionImages);

        // Validate đối tượng ProductReq
        Validator validator = jakarta.validation.Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ProductReq>> violations = validator.validate(productReq);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        // Xử lý tạo sản phẩm
        Product product = productService.handleCreateProduct(productReq);

        // Trả về response
        ApiResponse<Object> response = new ApiResponse<>();
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("Tạo sản phẩm thành công");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    // @PreAuthorize("hasAuthority('USER_READ')")

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateProduct(
            @PathVariable Long id,
            @RequestPart(required = false, value = "productData") String productDataJson,
            @RequestPart(required = false, value = "productImages") List<MultipartFile> productImages,
            @RequestPart(required = false, value = "promotionImages") List<MultipartFile> promotionImages,
            @RequestPart(required = false, value = "descriptionImages") List<MultipartFile> descriptionImages,
            BindingResult bindingResult) throws Exception {

        if (productDataJson == null || productDataJson.isEmpty()) {
            throw new IllegalArgumentException("Data product cannot be empty!");
        }

        System.out.println("[INFO] Received product data: " + productDataJson);

        ProductReq productReq;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            productReq = objectMapper.readValue(productDataJson, ProductReq.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid JSON data: " + e.getMessage());
        }

        // Set các danh sách ảnh
        productReq.setProductImages(productImages);
        productReq.setPromotionImages(promotionImages);
        productReq.setDescriptionImages(descriptionImages);

        // Validate đối tượng ProductReq
        Validator validator = jakarta.validation.Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ProductReq>> violations = validator.validate(productReq);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        // Xử lý cập nhật sản phẩm
        Product updatedProduct = productService.handleUpdateProduct(id, productReq);

        ApiResponse<Object> response = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Cập nhật sản phẩm thành công")
                .data(productMapper.mapToProductDetailRes(updatedProduct))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getProductById(@PathVariable Long id) throws Exception {
        ApiResponse<Object> response = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Get product by id successfully")
                .data(productMapper.mapToProductDetailRes(productService.handleGetProductById(id)))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping("")
    public ResponseEntity<ApiResponse<Object>> getAllProducts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "basic") String view,
            @Filter Specification<Product> spec,
            Sort sort) throws Exception {

        Pageable pageable = PageRequest.of(page, size, sort);

        // gọi service xử lý tùy theo role
        Object result = productService.handleGetProducts(spec, pageable, view);

        ApiResponse<Object> response = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Get all products successfully")
                .data(result)
                .build();
        return ResponseEntity.ok(response);
    }

}
