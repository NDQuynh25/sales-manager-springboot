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
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import com.example.sales_manager.service.ProductService;
import com.turkraft.springfilter.boot.Filter;
import com.example.sales_manager.entity.Product;
import com.example.sales_manager.entity.Role;
import org.springframework.http.MediaType;

@Controller
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;

    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Object>> addProduct(
            @RequestPart(required = false, value = "productData") String productDataJson,
            @RequestPart(required = false, value = "productImages") List<MultipartFile> productImages,
            @RequestPart(required = false, value = "promotionImages") List<MultipartFile> promotionImages,
            @RequestPart(required = false, value = "descriptionImages") List<MultipartFile> descriptionImages,
            BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);

        }
        System.out.println("[INFO] count product images: " + productImages.size());
        System.out.println("[INFO] count promotion images: " + promotionImages.size());
        System.out.println("[INFO] count description images: " + descriptionImages.size());
        System.out.println("[INFO] productDTO: " + productDataJson.toString());

        ApiResponse<Object> response = new ApiResponse<>();

        // Product product = productService.hanldeCreateProduct(productDTO);
        // response.setStatus(HttpStatus.CREATED.value());
        // response.setMessage("Create Product successfully");
        // response.setData(productService.mapProductToResProductDto(product));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getProductById(@PathVariable Long id) throws Exception {
        ApiResponse<Object> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Get product successfully");
        response.setData(productService.mapProductToProductDetailRes(productService.handleGetProductById(id)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<Object>> getAllProducts(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "false") Boolean isSummary,
            @Filter Specification<Product> spec,
            Sort sort) throws Exception {

        ApiResponse<Object> response = new ApiResponse<>();

        Pageable pageable = PageRequest.of(page, size, sort);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Get all products successfully");
        response.setData(productService.handleGetProducts(spec, pageable, isSummary));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
