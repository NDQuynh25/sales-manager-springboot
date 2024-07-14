package com.example.sales_manager.dto;

import java.sql.Date;

import com.example.sales_manager.util.constant.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;

public class ReqUpdateUserDto {

    /*
     * **ReqUpdateUserDto** is a data transfer object class that contains the fields required to update a user.
     * 7 columns, exclude (email, password, confirmPassword, facebookAccountId, googleAccountId, avatar, refreshToken)
     * avatar column is passed separately as form-data
     */
    @NotEmpty(message = "Fullname cannot be blank!")
    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @JsonProperty("gender")
    private GenderEnum gender;

    @JsonProperty("is_active")
    private Integer isActive;

    @JsonProperty("role_id")
    private Integer roleId;

    @JsonProperty("address")
    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    public ReqUpdateUserDto() {
    }

    public ReqUpdateUserDto(@NotEmpty(message = "Fullname cannot be blank!") String fullName, String phoneNumber,
            GenderEnum gender, Integer isActive, Integer roleId, String address, Date dateOfBirth) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.isActive = isActive;
        this.roleId = roleId;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
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
    
    

}

