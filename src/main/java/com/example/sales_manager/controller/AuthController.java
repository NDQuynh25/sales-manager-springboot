package com.example.sales_manager.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.sales_manager.dto.request.LoginReq;
import com.example.sales_manager.dto.request.RegisterReq;
import com.example.sales_manager.dto.response.LoginRes;
import com.example.sales_manager.dto.response.ApiResponse;
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

        public AuthController(AuthService authService, SecurityService securityService, UserService userService,
                        RoleService roleService) {
                this.authService = authService;
                this.securityService = securityService;
                this.userService = userService;
                this.roleService = roleService;
        }

        @PostMapping("/register")
        public ResponseEntity<ApiResponse<Object>> register(@Valid @RequestBody RegisterReq registerDto)
                        throws Exception {

                Boolean check = this.authService.handleRegister(registerDto);
                System.out.println(check);
                if (check) {
                        ApiResponse<Object> response = new ApiResponse<>(201, "Register success", "Register success",
                                        null);
                        return ResponseEntity.status(201).body(response);
                } else {
                        ApiResponse<Object> response = new ApiResponse<>(400, "Register failed", "Register failed",
                                        null);
                        return ResponseEntity.badRequest().body(response);
                }
        }

        @PostMapping("/login")
        public ResponseEntity<ApiResponse<Object>> login(@Valid @RequestBody LoginReq LoginReq,
                        BindingResult bindingResult) throws Exception {

                System.out.println(LoginReq);
                if (bindingResult.hasErrors()) {
                        throw new BindException(bindingResult);
                }
                LoginRes LoginRes = this.authService.handleLogin(LoginReq);

                // Create refresh token and update to database
                String refresh_token = this.securityService.createRefreshToken(LoginRes.getUser().getEmail(),
                                LoginRes);
                userService.handleUpdateRefreshTokenByEmail(LoginReq.getEmail(), refresh_token);

                // Set cookie
                ResponseCookie resCookies = ResponseCookie
                                .from("refresh-token", refresh_token)
                                .httpOnly(true)
                                .secure(true)
                                .maxAge(this.refreshTokenExpiration)
                                .path("/")
                                .build();

                ApiResponse<Object> response = new ApiResponse<>(
                                200,
                                null,
                                "Login success",
                                LoginRes);

                return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookies.toString()).body(response);
        }

        @GetMapping("/account")
        public ResponseEntity<ApiResponse<LoginRes>> account() throws Exception {

                String email = this.securityService.getCurrentUserLogin().isPresent()
                                ? securityService.getCurrentUserLogin().get()
                                : null;

                User user = this.userService.handleGetUserByEmail(email);

                LoginRes LoginRes = new LoginRes();

                LoginRes.User userDto = LoginRes.new User(
                                user.getId(),
                                user.getFullName(),
                                user.getEmail(),
                                user.getAvatar(),
                                this.roleService.mapRoleToRoleDto(
                                                this.roleService.handleGetRoleById(user.getRole().getId())));
                LoginRes.setUser(userDto);

                ApiResponse<LoginRes> response = new ApiResponse<>(
                                200,
                                null,
                                "Fetch account",
                                LoginRes);
                return ResponseEntity.ok().body(response);
        }

        @GetMapping("/refresh")
        public ResponseEntity<ApiResponse<Object>> refreshToken(
                        @CookieValue(name = "refresh-token") String refreshToken)
                        throws MissingRequestCookieException, Exception {
                System.out.println(">>> refresh token");
                // Handle refresh token
                LoginRes LoginRes = this.authService.handleRefreshToken(refreshToken);

                // Create new refresh token
                String new_refresh_token = this.securityService.createRefreshToken(LoginRes.getUser().getEmail(),
                                LoginRes);

                // Update new refresh token to database
                this.userService.handleUpdateRefreshTokenByEmail(LoginRes.getUser().getEmail(), new_refresh_token);

                // set cookie
                ResponseCookie resCookies = ResponseCookie
                                .from("refresh-token", new_refresh_token)
                                .httpOnly(true)
                                .secure(true)
                                .maxAge(this.refreshTokenExpiration)
                                .path("/")
                                .build();

                // create response
                ApiResponse<Object> response = new ApiResponse<>(
                                200,
                                null,
                                "Refresh token success",
                                LoginRes);

                return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookies.toString()).body(response);
        }

        @PostMapping("/logout")
        public ResponseEntity<ApiResponse<Object>> logout() throws Exception {

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

                ApiResponse<Object> response = new ApiResponse<>(
                                200,
                                null,
                                "Logout success",
                                null);

                return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookies.toString()).body(response);
        }

}
