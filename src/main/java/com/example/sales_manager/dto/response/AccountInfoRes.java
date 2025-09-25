package com.example.sales_manager.dto.response;


import com.example.sales_manager.dto.RoleDto;

import com.google.auto.value.AutoValue.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AccountInfoRes {
    private Long id;
    private String email;
    private String fullName;
    private String avatar;
    private Long cartId;
    private RoleDto role;
    

}
