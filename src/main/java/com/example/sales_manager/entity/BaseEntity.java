package com.example.sales_manager.entity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.MappedSuperclass;

import java.time.Instant;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.context.SecurityContextHolder;

@MappedSuperclass
public abstract class BaseEntity {

    private Integer isActive;

    private String createdBy;

    private String updatedBy;

    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        if (this.isActive == null ) {
            this.isActive = 1;
        }
        this.createdBy = SecurityContextHolder.getContext().getAuthentication() != null 
            ? SecurityContextHolder.getContext().getAuthentication().getName() 
            : "system";
        this.updatedBy = SecurityContextHolder.getContext().getAuthentication() != null
            ? SecurityContextHolder.getContext().getAuthentication().getName()
            : "system";
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();

    }
    @PreUpdate
    protected void onUpdate() {
        
        this.updatedAt = Instant.now();
        this.updatedBy = SecurityContextHolder.getContext().getAuthentication() != null
            ? SecurityContextHolder.getContext().getAuthentication().getName()
            : "system";
        System.out.println("onUpdate called: dateUpdated = " + this.updatedAt);
    }
    public Integer getIsActive() {
        return isActive;
    }
    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getUpdatedBy() {
        return updatedBy;
    }
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    public Instant getCreatedAt() {
        return createdAt;
        
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Getters and setters
    
}
