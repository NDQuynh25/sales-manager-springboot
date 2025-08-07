package com.example.sales_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;  
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OAuth2UserInfo {
    private String email;
    private String name;
    private String picture;
    
}
