package com.example.sales_manager.dto.request;

import java.sql.Date;
import java.time.LocalDate;

import com.example.sales_manager.util.validation.PhoneNumber;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.sales_manager.util.constant.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class UpdateUserReq {

    /*
     * **UpdateUserReq** is a data transfer object class that contains the fields
     * required to update a user.
     * 7 columns, exclude (email, password, confirmPassword, facebookAccountId,
     * googleAccountId, avatar, refreshToken)
     * avatar column is passed separately as form-data
     */
    @JsonProperty("id")
    private Long id;

    @NotBlank(message = "Full name cannot be blank!")
    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("phoneNumber")
    @NotBlank(message = "Phone number cannot be blank!")
    @PhoneNumber(message = "Phone number is invalid!")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @JsonProperty("gender")
    private GenderEnum gender;

    @JsonProperty("isActive")
    @NotNull(message = "Is active cannot be null!")
    @Min(value = 0, message = "Is active must be 0 or 1!")
    @Max(value = 1, message = "Is active must be 0 or 1!")
    private Integer isActive;

    @JsonProperty("roleId")
    @NotNull(message = "Role id cannot be blank!")
    private Long roleId;

    @JsonProperty("address")
    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("dateOfBirth")
    private LocalDate dateOfBirth;

    @JsonProperty("avatar")
    private MultipartFile avatarFile;

    public UpdateUserReq() {
    }

    public UpdateUserReq(Long id, String fullName, String phoneNumber,
            GenderEnum gender, Integer isActive, Long roleId, String address, LocalDate dateOfBirth,
            MultipartFile avatarFile) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.isActive = isActive;
        this.roleId = roleId;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.avatarFile = avatarFile;
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
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public MultipartFile getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(MultipartFile avatarFile) {
        this.avatarFile = avatarFile;
    }

}
