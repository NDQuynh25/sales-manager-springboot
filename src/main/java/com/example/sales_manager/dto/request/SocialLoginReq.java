package com.example.sales_manager.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialLoginReq {
    private String token; // ID token for OAuth2 login
}
