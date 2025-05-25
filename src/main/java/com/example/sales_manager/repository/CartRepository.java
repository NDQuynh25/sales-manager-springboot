package com.example.sales_manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.sales_manager.entity.Cart;
import com.example.sales_manager.entity.CartItem;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
    Cart save(Cart cart);
    void deleteById(Long id);
    Cart findByUserId(Long userId);


    
} 
    
