package com.example.sales_manager.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.sales_manager.repository.CartItemRepository;
import com.example.sales_manager.dto.ResultPagination;
import com.example.sales_manager.entity.CartItem;
import com.example.sales_manager.entity.Category;
import java.util.List;


@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    
    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public ResultPagination handleGetCartItems(Specification<Category> spec, Pageable pageable, Long cartId) {
        try {
            ResultPagination result = new ResultPagination();
            if (cartId != null) {
                spec = spec.and((root, query, criteriaBuilder) -> 
                    criteriaBuilder.equal(root.get("cartItemId"), cartId));
            }
            if (pageable == null) {
                pageable = Pageable.unpaged();
            }
            if (spec == null) {
                spec = (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            
         
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving cart items: " + e.getMessage(), e);
        }

        
    }

}
