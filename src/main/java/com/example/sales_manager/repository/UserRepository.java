package com.example.sales_manager.repository;

import com.example.sales_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User save(User user);
    void deleteById(Long id);
    User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
    List<User> findAll();
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    

    
} 