package com.example.sales_manager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import com.example.sales_manager.util.constant.GenderEnum;
import java.time.Instant;


import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;




@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "phone_number")
})
public class User { // 18 columns

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName; 

    private String email;

    private String phoneNumber;

    private String password;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private Integer roleId;

    @Column(columnDefinition = "TEXT")
    private String address;

    private Date dateOfBirth;

    @Column(columnDefinition = "TEXT")
    private String facebookAccountId;

    @Column(columnDefinition = "TEXT")
    private String googleAccountId;

    @Column(columnDefinition = "TEXT")
    private String avatar;

    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    private Integer isActive;

    private String createdBy;

    private String updatedBy;

    private Instant createdAt;

    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        this.isActive = 1;
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
    }

    public User() {
    }
    public User(Long id, String fullName, String email, String phoneNumber, String password, GenderEnum gender,
            Integer roleId, String address, Date dateOfBirth, String facebookAccountId, String googleAccountId,
            String avatar, String refreshToken, String createdBy, String updatedBy) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.gender = gender;
        this.roleId = roleId;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.facebookAccountId = facebookAccountId;
        this.googleAccountId = googleAccountId;
        this.avatar = avatar;
        this.refreshToken = refreshToken;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullname) {
        this.fullName = fullname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public GenderEnum getGender() {
        return gender;
    }
    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }
    public Integer getRoleId() {
        return roleId;
    }
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getFacebookAccountId() {
        return facebookAccountId;
    }
    public void setFacebookAccountId(String facebookAccountId) {
        this.facebookAccountId = facebookAccountId;
    }
    public String getGoogleAccountId() {
        return googleAccountId;
    }
    public void setGoogleAccountId(String googleAccountId) {
        this.googleAccountId = googleAccountId;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
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
    
}

    