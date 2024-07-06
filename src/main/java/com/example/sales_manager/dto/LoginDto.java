package com.example.sales_manager.dto;


import com.example.sales_manager.util.validation.CheckEmail;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;

@GroupSequence({LoginDto.class, LoginDto.Check1.class, LoginDto.Check2.class})
public class LoginDto {

    @NotBlank(message = "Email cannot be empty", groups = Check1.class)
    @CheckEmail(message = "Email is invalid", groups = Check2.class)
    private String email;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    public LoginDto() {
    }

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
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

    public interface Check1 {
    }
    public interface Check2 {
    }
    
}
