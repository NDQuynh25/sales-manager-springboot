package com.example.sales_manager.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.sales_manager.dto.request.ReqLoginDto;
import com.example.sales_manager.dto.request.ReqRegisterDto;
import com.example.sales_manager.dto.response.ResLoginDto;
import com.example.sales_manager.dto.response.RestResponse;
import com.example.sales_manager.entity.User;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.sales_manager.service.AuthService;
import com.example.sales_manager.service.RoleService;
import com.example.sales_manager.service.SecurityService;
import com.example.sales_manager.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    private final RoleService roleService;

    private final SecurityService securityService;

    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

    public AuthController(AuthService authService, SecurityService securityService, UserService userService, RoleService roleService) {
        this.authService = authService;
        this.securityService = securityService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("/register")
    public ResponseEntity<RestResponse<Object>> register(@Valid @RequestBody ReqRegisterDto registerDto) throws Exception{
        
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
        
        System.out.println(reqLoginDto);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        ResLoginDto resLoginDto = this.authService.handleLogin(reqLoginDto);
     
        // Create refresh token and update to database
        String refresh_token = this.securityService.createRefreshToken(resLoginDto.getUser().getEmail(), resLoginDto);
        userService.handleUpdateRefreshTokenByEmail(reqLoginDto.getEmail(), refresh_token);
       

        // Set cookie
        ResponseCookie resCookies = ResponseCookie
            .from("refresh-token", refresh_token)
            .httpOnly(true)
            .secure(true)
            .maxAge(this.refreshTokenExpiration)
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
    public ResponseEntity<RestResponse<ResLoginDto>> account() throws Exception{
       
        String email = this.securityService.getCurrentUserLogin().isPresent() ? securityService.getCurrentUserLogin().get() : null;

        User user = this.userService.handleGetUserByEmail(email);

        ResLoginDto resLoginDto = new ResLoginDto();
        
        ResLoginDto.User userDto = resLoginDto.new User(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getAvatar(),
            this.roleService.mapRoleToRoleDto(this.roleService.handleGetRoleById(user.getRoleId()))   
        );
        resLoginDto.setUser(userDto);


        RestResponse<ResLoginDto> response = new RestResponse<>(
            200, 
            null, 
            "Fetch account", 
            resLoginDto);
        return ResponseEntity.ok().body(response);
    }

    
    @GetMapping("/refresh")
    public ResponseEntity<RestResponse<Object>> refreshToken( @CookieValue(name = "refresh-token") String refreshToken) throws MissingRequestCookieException, Exception{
        System.out.println(">>> refresh token");
        // Handle refresh token
        ResLoginDto resLoginDto = this.authService.handleRefreshToken(refreshToken);

        // Create new refresh token
        String new_refresh_token = this.securityService.createRefreshToken(resLoginDto.getUser().getEmail(), resLoginDto);

        // Update new refresh token to database
        this.userService.handleUpdateRefreshTokenByEmail(resLoginDto.getUser().getEmail(), new_refresh_token);

        // set cookie
        ResponseCookie resCookies = ResponseCookie
            .from("refresh-token", new_refresh_token)
            .httpOnly(true)
            .secure(true)
            .maxAge(this.refreshTokenExpiration)
            .path("/")
            .build();

        // create response
        RestResponse<Object> response = new RestResponse<>(
            200, 
            null, 
            "Refresh token success", 
            resLoginDto);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookies.toString()).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<RestResponse<Object>> logout() throws Exception{
       
        // Delete the refresh token in the database and security context
        this.authService.handleLogout();
        
        // Remove cookie
        ResponseCookie resCookies = ResponseCookie
            .from("refresh-token", null) // remove cookie
            .httpOnly(true)
            .secure(true)
            .maxAge(0) // remove cookie
            .path("/")
            .build();

        RestResponse<Object> response = new RestResponse<>(
            200, 
            null, 
            "Logout success", 
            null);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookies.toString()).body(response);
    }

}
