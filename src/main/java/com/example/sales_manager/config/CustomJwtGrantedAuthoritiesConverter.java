package com.example.sales_manager.config;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CustomJwtGrantedAuthoritiesConverter {

    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new HashSet<>();

        // Trích xuất thông tin từ user claim
        Map<String, Object> userClaim = jwt.getClaim("user");
        if (userClaim != null) {
            // Trích xuất role
            Map<String, Object> roleClaim = (Map<String, Object>) userClaim.get("role");
            if (roleClaim != null) {
                String roleName = (String) roleClaim.get("roleName");
                if (roleName != null) {
                    authorities.add(new SimpleGrantedAuthority(roleName));
                }

                // Trích xuất permissions từ role
                List<Map<String, Object>> permissions = (List<Map<String, Object>>) roleClaim.get("permissions");
                if (permissions != null) {
                    for (Map<String, Object> permission : permissions) {
                        String permissionName = (String) permission.get("permissionName");
                        if (permissionName != null) {
                            authorities.add(new SimpleGrantedAuthority(permissionName));
                        }
                    }
                }
            }
        }

        System.out.println(">>> (CustomJwtGrantedAuthoritiesConverter.java line 40) Authorities: " + authorities);
        return authorities;
    }
}

