package com.example.sales_manager.repository;

import org.springframework.data.domain.Page;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.sales_manager.entity.CartItem;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
   
  
    Page<CartItem> findAll(Specification<CartItem> spec, Pageable pageable);
} 