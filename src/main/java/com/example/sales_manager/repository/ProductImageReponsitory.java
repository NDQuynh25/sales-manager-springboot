package com.example.sales_manager.repository;

import com.example.sales_manager.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageReponsitory extends JpaRepository<ProductImage, Long> {
    
    void deleteByProductId(Long productId);

    ProductImage save(ProductImage productImage);

    
}
