package com.example.sales_manager.repository;

import com.example.sales_manager.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User save(User user);

    void deleteById(Long id);

    User findByEmail(String email);

    User findByPhoneNumber(String phoneNumber);

    Page<User> findAll(Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    boolean existsByPhoneNumber(String phoneNumber);

    User findByEmailAndRefreshToken (String email, String refreshToken);

    Page<User> findAll(Specification<User> spec, Pageable pageable);
    

    
} 