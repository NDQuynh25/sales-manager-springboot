package com.example.sales_manager.repository;

import com.example.sales_manager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
    Category save(Category category);
    void deleteById(Long id);
    Category findByName(String name);
    List<Category> findAll();
    
}
