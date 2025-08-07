package com.example.sales_manager.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.sales_manager.entity.Cart;
import com.example.sales_manager.entity.CartItem;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
    Cart save(Cart cart);
    void deleteById(Long id);
    Cart findByUserId(Long userId);

    

    //Page<CartItem> findCartItemsByCartId(Long cartId, Specification<CartItem> spec, Pageable pageable);


    
} 
    
