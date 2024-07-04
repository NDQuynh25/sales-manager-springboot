package com.example.sales_manager.dto;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import com.example.sales_manager.validation.CheckEmail;
import com.example.sales_manager.validation.PhoneNumber;
import java.sql.Date;

@GroupSequence({UserDto.class, UserDto.Check1.class, UserDto.Check2.class}) //Check order, if check1 fails, there is no need to check check2
public class UserDto {


    @NotEmpty(message = "Fullname cannot be blank!", groups = Check1.class)
    private String fullname;

    @NotBlank(message = "Email cannot be blank!", groups = Check1.class)
    @CheckEmail(message = "Invalid email format", groups = Check2.class)
    private String email;

    
    @NotBlank(message = "Phone number cannot be blank!", groups = Check1.class)
    @PhoneNumber(message = "Invalid phone number format", groups = Check2.class)
    private String phoneNumber;

    @NotBlank(message = "Password cannot be blank!", groups = Check1.class)
    private String password;

    @NotBlank(message = "Confirm password cannot be blank!", groups = Check1.class)
    private String confirmPassword;

    private Integer roleId;

    private String address;

    private Date DateOfBirth;

    private String facebookAccountId;

    private String googleAccountId;

    public UserDto() {
    }

    public UserDto(String fullname, String email, String phoneNumber, String password, String confirmPassword, Integer roleId,
            String address, Date dateOfBirth, String facebookAccountId, String googleAccountId) {
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.roleId = roleId;
        this.address = address;
        DateOfBirth = dateOfBirth;
        this.facebookAccountId = facebookAccountId;
        this.googleAccountId = googleAccountId;
    }

    // Getters and setters

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
        return DateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        DateOfBirth = dateOfBirth;
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
