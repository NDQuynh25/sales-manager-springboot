package com.example.sales_manager.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sales_manager.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    void deleteById(Long id);

    Product findProductById(Long id);

    Product findProductByIdAndIsActive(Long id, Integer isActive);

    Product save(Product product);

    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM categories_products WHERE category_id = :categoryId", nativeQuery = true)
    Long countProductsByCategoryId(@Param("categoryId") Long categoryId);
}
