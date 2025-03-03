package com.example.sales_manager.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.sales_manager.dto.request.ReqSkuDto;
import com.example.sales_manager.dto.response.ResSkuDto;
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
    public SKU handleCreateSku(ReqSkuDto reqSkuDto) {
        SKU sku = new SKU();
        sku.setSkuCode(reqSkuDto.getSkuCode());
        sku.setOption1(reqSkuDto.getOption1());
        sku.setOption2(reqSkuDto.getOption2());
        sku.setOriginalPrice(reqSkuDto.getOriginalPrice());
        sku.setSellingPrice(reqSkuDto.getSellingPrice());
        sku.setStock(reqSkuDto.getStock());
        sku.setQuantitySold(reqSkuDto.getQuantitySold());
        sku.setIsActive(reqSkuDto.getIsActive());
        
        skuRepository.save(sku);
        return sku;

    }




    
}
