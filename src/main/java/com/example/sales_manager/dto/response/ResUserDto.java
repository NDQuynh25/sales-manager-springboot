package com.example.sales_manager.dto.response;

import java.sql.Date;
import java.time.Instant;
import com.example.sales_manager.util.constant.GenderEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class ResUserDto { // 14 columns, exclude (password, refreshToken, facebookAccountId, googleAccountId)

    private Long id;

    private String fullname; 

    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private ResRoleDto role;

    private String address;

    private Date dateOfBirth;

    private String avatar;

    private Integer isActive;

    private String createdBy;

    private String updatedBy;

    private Instant createdAt;

    private Instant updatedAt;

    public ResUserDto() {
    }

    public ResUserDto(Long id, String fullname, String email, String phoneNumber, GenderEnum gender,
            ResRoleDto role, String address, Date dateOfBirth, String avatar, Integer isActive, String createdBy,
            String updatedBy, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.role = role;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.avatar = avatar;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public ResRoleDto getRole() {
        return role;
    }

    public void setRole(ResRoleDto role) {
        this.role = role;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
