package com.example.sales_manager.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class LoginRes {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("account_info")
    private AccountInfoRes accountInfo;

}
