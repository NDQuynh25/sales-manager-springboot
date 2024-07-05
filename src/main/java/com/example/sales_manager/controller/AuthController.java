package com.example.sales_manager.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.sales_manager.dto.LoginDto;
import com.example.sales_manager.dto.RegisterDto;
import com.example.sales_manager.dto.ResLoginDto;
import com.example.sales_manager.exception.RestResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.sales_manager.service.AuthService;
import com.example.sales_manager.service.SecurityService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    private final SecurityService securityService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(AuthService authService, SecurityService securityService, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.authService = authService;
        this.securityService = securityService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/register")
    public ResponseEntity<RestResponse<Object>> register(@Valid @RequestBody RegisterDto registerDto) throws Exception{
        Boolean check = authService.handleRegister(registerDto);
        System.out.println(check);
        if (check) {
            RestResponse<Object> response = new RestResponse<>(201, "Register success", "Register success", null);
            return ResponseEntity.status(201).body(response);
        } else {
            RestResponse<Object> response = new RestResponse<>(400, "Register failed", "Register failed", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<RestResponse<Object>> login(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        // Nạp username và password vào security 
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        
        // Xác thực => loadUserByUsername trong
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // Create token
        String access_token = securityService.createToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResLoginDto resLoginDto = new ResLoginDto();
        resLoginDto.setAccessToken(access_token);
        RestResponse<Object> response = new RestResponse<>(
            200, 
            null, 
            "Login success", 
            resLoginDto);

        return ResponseEntity.ok().body(response);
    }
    
}
