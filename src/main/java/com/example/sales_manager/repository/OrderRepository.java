package com.example.sales_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.sales_manager.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    Order save(Order order);

    void deleteById(Long id);
}
