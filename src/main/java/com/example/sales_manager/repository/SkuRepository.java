package com.example.sales_manager.repository;

import com.example.sales_manager.entity.SKU;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkuRepository extends JpaRepository<SKU, Long> {
    
    SKU save(SKU sku);

    void deleteById(Long id);

    SKU findSKUById(Long id);

    
}
