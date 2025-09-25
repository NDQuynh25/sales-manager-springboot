package com.example.sales_manager.dto.response;


import java.time.Instant;
import java.time.LocalDate;

import com.example.sales_manager.util.constant.GenderEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;



@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRes { // 14 columns, exclude (password, refreshToken, facebookAccountId,
                       // googleAccountId)

    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private Long cartId;

    private RoleRes role;

    private String address;

    private LocalDate dateOfBirth;

    private String avatar;

    private Integer isActive;

    private String createdBy;

    private String updatedBy;

    private Instant createdAt;

    private Instant updatedAt;

}
