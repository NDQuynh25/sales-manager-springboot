package com.example.sales_manager.repository;

import com.example.sales_manager.entity.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
    Category save(Category category);
    void deleteById(Long id);

    Category findCategoryById(Long id);
    Page<Category> findAll(Specification<Category> spec , Pageable pageable);
   
    List<Category> findAll();

    
    
}
