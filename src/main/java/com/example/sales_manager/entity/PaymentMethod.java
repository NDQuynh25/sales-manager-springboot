package com.example.sales_manager.entity;

import com.example.sales_manager.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "payment_methods", uniqueConstraints = {
    @UniqueConstraint(columnNames = "id")
})
public class PaymentMethod extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "method_name", nullable = false, length = 50)
    private String methodName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;


    public PaymentMethod() {
    }

    public PaymentMethod(String methodName, String description) {
        this.methodName = methodName;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
