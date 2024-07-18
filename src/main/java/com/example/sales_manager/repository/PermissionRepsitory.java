package com.example.sales_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.example.sales_manager.entity.Permission;

@Repository
public interface PermissionRepsitory extends JpaRepository<Permission, Long> {
    
    Permission save(Permission permission);

    Permission findByName(String name);

    Page<Permission> findAll(Pageable pageable);
    
    void deleteById(Long id);

    @Query("SELECT p.name FROM permissions p WHERE p.id= :id")
    String getNameById(Long id);
    
}
