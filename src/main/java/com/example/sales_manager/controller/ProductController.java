package com.example.sales_manager.controller;

import com.example.sales_manager.dto.request.ReqProductDto;
import com.example.sales_manager.dto.response.RestResponse;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.validation.BindException;

import org.springframework.web.bind.annotation.*;
import com.example.sales_manager.service.ProductService;
import com.example.sales_manager.entity.Product;

@Controller
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;

    }

    // @PostMapping()
    // public ResponseEntity<RestResponse<Object>> addProduct(
    //         @Valid @RequestBody ReqProductDto productDTO,
    //         BindingResult bindingResult) throws Exception {
        
    //     if (bindingResult.hasErrors()) {
    //         throw new BindException(bindingResult);
    //     }

    //     RestResponse<Object> response = new RestResponse<>();
    //     //try {
    //         Product product = productService.hanldeCreateProduct(productDTO);
    //         response.setStatus(HttpStatus.CREATED.value());
    //         response.setMessage("Create Product successfully");
    //         response.setData(productService.mapProductToResProductDto(product));
    //         return ResponseEntity.status(HttpStatus.CREATED).body(response);

    //     // } catch (Exception e) {

    //     //     response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    //     //     response.setMessage("Error Creating Product");
    //     //     response.setData(null);
    //     //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            
    //     // }
    // }

   
}
