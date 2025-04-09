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
import java.util.ArrayList;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/v1/skus")
public class SkuController {

    private final SkuService skuService;

    public SkuController(SkuService skuService) {
        this.skuService = skuService;
    }

    // @PostMapping("")
    // public ResponseEntity<RestResponse<Object>> addSku(
    // @Valid @RequestBody List<SkuReq> skus,
    // BindingResult bindingResult) throws Exception {

    // List<SkuRes> resSkus = new ArrayList<>();

    // for (SkuReq sku : skus) {
    // resSkus.add(skuService.mapSKUToSkuRes(skuService.handleCreateSku(sku)));
    // }

    // RestResponse<Object> response = new RestResponse<>();
    // response.setStatus(HttpStatus.CREATED.value());
    // response.setMessage("Create user successfully");
    // response.setData(resSkus);

    // return ResponseEntity.status(HttpStatus.CREATED).body(response);
    // }

}
