package com.example.sales_manager.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.sales_manager.entity.Role;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    Role save(Role role);
    Role findByName(String name);

    Page<Role> findAll(Pageable pageable);

    @Query("SELECT r.id FROM Role r WHERE r.name = :name")
    Integer getIdbyName(String name);

    void deleteById(Long id);
    
} 
