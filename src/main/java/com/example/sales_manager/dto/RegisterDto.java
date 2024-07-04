package com.example.sales_manager.dto;

import jakarta.validation.constraints.NotNull;

import com.example.sales_manager.validation.CheckEmail;
import com.example.sales_manager.validation.PhoneNumber;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class RegisterDto {

    @NotEmpty(message = "User's fullname cannot be empty!")
    private String fullname;

    @NotBlank(message = "User's email cannot be empty!")
    @CheckEmail(message = "Invalid email format!")
    private String email;

    @NotBlank(message = "User's phone number cannot be empty!")
    @PhoneNumber(message = "Invalid phone number format!")
    private String phoneNumber;

    @NotBlank(message = "User's password cannot be empty!")
    private String password;

    @NotBlank(message = "User's confirm password cannot be empty!")
    private String confirmPassword;

    public RegisterDto() {
    }
    

    public RegisterDto(String fullname, String email, String phoneNumber, String password, String confirmPassword) {
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.confirmPassword = confirmPassword;
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
}