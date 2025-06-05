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
        sku.setOption1(SkuReq.getOption1());
        sku.setOption2(SkuReq.getOption2());
        sku.setOriginalPrice(SkuReq.getOriginalPrice());
        sku.setStock(SkuReq.getStock());

        sku.setIsActive(SkuReq.getIsActive());

        skuRepository.save(sku);
        return sku;

    }

    

}
