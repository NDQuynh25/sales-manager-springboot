package com.example.sales_manager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.sales_manager.dto.request.SkuReq;
import com.example.sales_manager.dto.response.ApiResponse;
import com.example.sales_manager.service.SkuService;
import com.example.sales_manager.dto.response.SkuRes;
import com.example.sales_manager.mapper.SkuMapper;

import java.util.ArrayList;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/api/v1/skus")
public class SkuController {

    private final SkuService skuService;

    private final SkuMapper skuMapper;

    public SkuController(SkuService skuService, SkuMapper skuMapper) {
        this.skuMapper = skuMapper;
        this.skuService = skuService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getSkuById(@PathVariable Long id) throws Exception {
        ApiResponse<Object> response = ApiResponse.builder()
            .status(HttpStatus.OK.value())
            .message("Get product by id successfully")
            .data(skuMapper.mapToSkuRes(skuService.handleGetSkuById(id)))
            .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
