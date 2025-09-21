package com.example.sales_manager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data; 
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;



@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("permissionName")
    private String permissionName;

    @JsonProperty("apiAccess")
    private String apiAccess;

    @JsonProperty("method")
    private String method;

   
}
