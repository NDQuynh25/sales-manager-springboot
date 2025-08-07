package com.example.sales_manager.dto.request;

import com.example.sales_manager.util.validation.CheckEmail;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;

@GroupSequence({ LoginReq.class, LoginReq.Check1.class, LoginReq.Check2.class })
public class LoginReq {

    @NotBlank(message = "Email cannot be empty", groups = Check1.class)
    @CheckEmail(message = "Email is invalid", groups = Check2.class)
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    public LoginReq() {
    }

    public LoginReq(String email, String password) {
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
