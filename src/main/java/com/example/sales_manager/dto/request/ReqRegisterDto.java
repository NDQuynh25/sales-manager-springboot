package com.example.sales_manager.dto.request;

import com.example.sales_manager.util.validation.CheckEmail;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ReqRegisterDto {

    //@NotEmpty(message = "User's fullname cannot be empty!")
    @JsonProperty("full_name")
    private String fullname;

    @NotBlank(message = "User's email cannot be empty!")
    @CheckEmail(message = "Invalid email format!")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "User's password cannot be empty!")
    @JsonProperty("password")
    private String password;

    
    @NotBlank(message = "User's confirm password cannot be empty!")
    @NotEmpty(message = "User's confirm password cannot be empty!")
    @NotNull(message = "User's confirm password cannot be empty!")
    @JsonProperty("confirmPassword")
    private String confirmPassword;

    public ReqRegisterDto() {
    }
    

    public ReqRegisterDto(String fullname, String email, String password, String confirmPassword) {
        this.fullname = fullname;
        this.email = email;
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