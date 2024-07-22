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
            String role = (String) userClaim.get("role");
            if (role != null) {
                authorities.add(new SimpleGrantedAuthority(role));
            }

            // Trích xuất permissions
            List<String> permissions = (List<String>) userClaim.get("permissions");
            if (permissions != null) {
                permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));
            }
        }
        System.out.println(">>> Authorities: " + authorities);
        return authorities;
    }
}

