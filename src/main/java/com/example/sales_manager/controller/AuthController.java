package com.example.sales_manager.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.sales_manager.dto.ReqLoginDto;
import com.example.sales_manager.dto.RegisterDto;
import com.example.sales_manager.dto.ResLoginDto;
import com.example.sales_manager.entity.User;
import com.example.sales_manager.exception.RestResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.sales_manager.service.AuthService;
import com.example.sales_manager.service.SecurityService;
import com.example.sales_manager.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    private final SecurityService securityService;

    public AuthController(AuthService authService, SecurityService securityService, UserService userService) {
        this.authService = authService;
        this.securityService = securityService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RestResponse<Object>> register(@Valid @RequestBody RegisterDto registerDto) throws Exception{
        Boolean check = this.authService.handleRegister(registerDto);
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
    public ResponseEntity<RestResponse<Object>> login(@Valid @RequestBody ReqLoginDto reqLoginDto, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        ResLoginDto resLoginDto = this.authService.handleLogin(reqLoginDto);

        // Create refresh token and update to database
        String refresh_token = securityService.createRefreshToken(resLoginDto);
        userService.handleUpdateRefreshTokenByEmail(reqLoginDto.getEmail(), refresh_token);

        // Set cookie
        ResponseCookie resCookies = ResponseCookie
            .from("refresh-token", refresh_token)
            .httpOnly(true)
            .secure(true)
            .maxAge(30 * 24 * 60 * 60)
            .path("/")
            .build();

        RestResponse<Object> response = new RestResponse<>(
            200, 
            null, 
            "Login success", 
            resLoginDto);
        
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookies.toString()).body(response);
    }
    
    @GetMapping("/account")
    public ResponseEntity<RestResponse<Object>> account() {

        String email = this.securityService.getCurrentUserLogin().isPresent() ? securityService.getCurrentUserLogin().get() : null;

        User user = this.userService.handleGetUserByEmail(email);

        ResLoginDto resLoginDto = new ResLoginDto();
        ResLoginDto.User userDto = resLoginDto.new User(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getRoleId()
        );


        RestResponse<Object> response = new RestResponse<>(
            200, 
            null, 
            "Fetch account", 
            userDto);
        return ResponseEntity.ok().body(response);
    }

}
