package com.example.sales_manager.util;


import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.example.sales_manager.dto.PermissionDto;
import com.example.sales_manager.dto.RoleDto;
import com.example.sales_manager.dto.response.AccountInfoRes;


public class SecurityUtils {
    public static AccountInfoRes getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountInfoRes accountInfoRes = new AccountInfoRes();
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = (Jwt) jwtAuth.getPrincipal();
            Map<String, Object> userClaim = jwt.getClaim("user");
            if (userClaim != null) {
                
                accountInfoRes.setId(Long.valueOf((Long) userClaim.get("id")));
                accountInfoRes.setEmail((String) userClaim.get("email"));
                accountInfoRes.setFullName((String) userClaim.get("fullName"));
                accountInfoRes.setAvatar((String) userClaim.get("avatar"));
                accountInfoRes.setCartId(userClaim.get("cartId") != null ? Long.valueOf((Long) userClaim.get("cartId")) : null);
                
                Map<String, Object> roleClaim = (Map<String, Object>) userClaim.get("role");
                RoleDto roleDto = new RoleDto();
                
                
                
                   
                if (roleClaim != null) {
                    roleDto.setId(roleClaim.get("id") != null ? Long.valueOf((Long) roleClaim.get("id")) : null);
                    roleDto.setRoleName(roleClaim.get("roleName") != null ? (String) roleClaim.get("roleName") : null);

                    
                    List<Map<String, Object>> permissions = (List<Map<String, Object>>) roleClaim.get("permissions");
                    if (permissions != null) {
                        Set<PermissionDto> permissionDtos = new HashSet<>();
                        for (Map<String, Object> permission : permissions) {
                            PermissionDto permissionDto = PermissionDto.builder()
                                    .id(permission.get("id") != null ? Long.valueOf((Long) permission.get("id")) : null)
                                    .permissionName(permission.get("permissionName") != null ? (String) permission.get("permissionName") : null)
                                    .apiAccess(permission.get("apiAccess") != null ? (String) permission.get("apiAccess") : null)
                                    .method(permission.get("method") != null ? (String) permission.get("method") : null)
                                    .build();
                            permissionDtos.add(permissionDto);
                        }
                        roleDto.setPermissions(permissionDtos);
                    }
                }
                accountInfoRes.setRole(roleDto);
                

               
            }
        }
        return accountInfoRes; 
        
    }
}
