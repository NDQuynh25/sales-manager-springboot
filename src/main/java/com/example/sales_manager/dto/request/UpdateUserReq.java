package com.example.sales_manager.dto.request;

import java.sql.Date;
import java.time.LocalDate;

import com.example.sales_manager.util.validation.PhoneNumber;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.web.multipart.MultipartFile;

import com.example.sales_manager.util.constant.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue.Builder;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;



@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

   

}
