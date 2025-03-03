package com.example.sales_manager.dto.request;


import com.example.sales_manager.util.validation.CheckEmail;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;

@GroupSequence({ReqLoginDto.class, ReqLoginDto.Check1.class, ReqLoginDto.Check2.class})
public class ReqLoginDto {

    @NotBlank(message = "Email cannot be empty", groups = Check1.class)
    @CheckEmail(message = "Email is invalid", groups = Check2.class)
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    public ReqLoginDto() {
    }

    public ReqLoginDto(String email, String password) {
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
