package com.example.sales_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sales_manager.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    void deleteById(Long id);

    Product findProductById(Long id);

    Product save(Product product);

    @Query(value = "SELECT COUNT(*) FROM categories_products WHERE category_id = :categoryId", nativeQuery = true)
    Long countProductsByCategoryId(@Param("categoryId") Long categoryId);
}
