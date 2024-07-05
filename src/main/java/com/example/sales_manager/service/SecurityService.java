package com.example.sales_manager.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.example.sales_manager.config.SecurityConfiguration;
import com.nimbusds.jose.util.Base64;

@Service
public class SecurityService {

    private final JwtEncoder jwtEncoder;

    @Value("${jwt.base64-secret}")
    private String jwtKey;

    @Value("${jwt.token-validity-in-seconds}")
    private long expiration;

    public SecurityService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }


    public String createToken(Authentication authentication) {
        Instant now = Instant.now(); 
        Instant validity = now.plus(this.expiration, ChronoUnit.SECONDS); 
     
 
        // @formatter:off 
        JwtClaimsSet claims = JwtClaimsSet.builder() 
            .issuedAt(now) 
            .expiresAt(validity) 
            .subject(authentication.getName()) 
            .claim("ok", authentication) 
            .build(); 
 
        JwsHeader jwsHeader = JwsHeader.with(SecurityConfiguration.JWT_ALGORITHM).build(); 
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
    
}
