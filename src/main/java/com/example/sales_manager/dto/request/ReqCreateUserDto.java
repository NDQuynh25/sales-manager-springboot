package com.example.sales_manager.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import com.example.sales_manager.util.constant.GenderEnum;
import com.example.sales_manager.util.validation.CheckEmail;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@GroupSequence({ReqCreateUserDto.class, ReqCreateUserDto.Check1.class, ReqCreateUserDto.Check2.class}) //Check order, if check1 fails, there is no need to check check2
public class ReqCreateUserDto { 
    
    /* **ReqCreateUserDto** is a data transfer object class that contains the fields required to create a new user.
     * 11 columns, include(confirmPassword),
     * exclude (id, refreshToken, createdBy, updatedBy, createdAt, updatedAt, isActive),
     * avatarFile column is passed separately as form-data 
    */
   


    @NotEmpty(message = "Fullname cannot be blank!", groups = Check1.class)
    @JsonProperty("full_name")
    private String fullName;

    @NotBlank(message = "Email cannot be blank!", groups = Check1.class)
    @CheckEmail(message = "Invalid email format", groups = Check2.class)
    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "Password cannot be blank!", groups = Check1.class)
    @JsonProperty("password")
    private String password;

    @NotBlank(message = "Confirm password cannot be blank!", groups = Check1.class)
    @JsonProperty("confirm_password")
    private String confirmPassword;

    @Enumerated(EnumType.STRING)
    @JsonProperty("gender")
    private GenderEnum gender;

    @JsonProperty("role_id")
    private Long roleId;

    @JsonProperty("address")
    private String address;

    
    @JsonProperty("avatar_file")
    private MultipartFile avatarFile;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty("facebook_account_id")
    private String facebookAccountId;

    @JsonProperty("google_account_id")
    private String googleAccountId;

    public ReqCreateUserDto() {
    }

    
    public ReqCreateUserDto(String fullName, String email, String phoneNumber, String password, String confirmPassword, GenderEnum gender, Long roleId,
            String address, MultipartFile avatarFile, LocalDate dateOfBirth, String facebookAccountId, String googleAccountId) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.gender = gender;
        this.roleId = roleId;
        this.address = address;
        this.avatarFile = avatarFile;
        this.dateOfBirth = dateOfBirth;
        this.facebookAccountId = facebookAccountId;
        this.googleAccountId = googleAccountId;
    }

    // Getters and setters
    
    public String getFullName() {
        return fullName;
    }
    @JsonProperty("full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public GenderEnum getGender() {
        return this.gender;
    }
    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public MultipartFile getAvatarFile() {
        return avatarFile;
    }
    public void setAvatarFile(MultipartFile avatarFile) {
        this.avatarFile = avatarFile;
    }
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
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

    public interface Check1 {}
    public interface Check2 {}
}
