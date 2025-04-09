package com.example.sales_manager.service;

import org.springframework.stereotype.Service;


import com.example.sales_manager.dto.request.SkuReq;
import com.example.sales_manager.dto.response.SkuRes;
import com.example.sales_manager.entity.SKU;
import com.example.sales_manager.repository.SkuRepository;

import jakarta.transaction.Transactional;

@Service
public class SkuService {

    private final SkuRepository skuRepository;

    private final FileService fileService;

    public SkuService(SkuRepository skuRepository, FileService fileService) {
        this.skuRepository = skuRepository;
        this.fileService = fileService;

    }

    @Transactional
    public SKU handleCreateSku(SkuReq SkuReq) {
        SKU sku = new SKU();
        sku.setSkuCode(SkuReq.getSkuCode());
        sku.setOption1(SkuReq.getOption1());
        sku.setOption2(SkuReq.getOption2());
        sku.setOriginalPrice(SkuReq.getOriginalPrice());
        sku.setSellingPrice(SkuReq.getSellingPrice());
        sku.setStock(SkuReq.getStock());
        sku.setQuantitySold(SkuReq.getQuantitySold());
        sku.setIsActive(SkuReq.getIsActive());

        skuRepository.save(sku);
        return sku;

    }

    public SkuRes mapSKUToSkuRes(SKU sku) {
        SkuRes skuRes = new SkuRes();
        skuRes.setId(sku.getId());
        skuRes.setSkuCode(sku.getSkuCode());
        skuRes.setOption1(sku.getOption1());
        skuRes.setOption2(sku.getOption2());
        skuRes.setOriginalPrice(sku.getOriginalPrice());
        skuRes.setSellingPrice(sku.getSellingPrice());
        skuRes.setStock(sku.getStock());
        skuRes.setDiscount(sku.getDiscount());
        skuRes.setIsActive(sku.getIsActive());
        skuRes.setQuantitySold(sku.getQuantitySold());

        return skuRes;
    }

}
