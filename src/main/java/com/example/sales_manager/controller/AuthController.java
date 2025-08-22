package com.example.sales_manager.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cloudinary.provisioning.Account;
import com.example.sales_manager.dto.request.LoginReq;
import com.example.sales_manager.dto.request.RegisterReq;
import com.example.sales_manager.dto.response.LoginRes;
import com.example.sales_manager.dto.response.ApiResponse;
import com.example.sales_manager.entity.User;
import com.example.sales_manager.exception.IdInvaildException;

import jakarta.validation.Valid;
import lombok.extern.java.Log;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.Socket;

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
import com.example.sales_manager.util.constant.AuthProviderEnum;
import com.example.sales_manager.dto.request.SocialLoginReq;
import com.example.sales_manager.dto.response.AccountInfoRes;
import com.example.sales_manager.mapper.UserMapper;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

        private final AuthService authService;

        private final UserService userService;

        private final UserMapper userMapper;

        private final RoleService roleService;

        private final SecurityService securityService;

        @Value("${jwt.refresh-token-validity-in-seconds}")
        private long refreshTokenExpiration;

        public AuthController(AuthService authService, SecurityService securityService, UserService userService, UserMapper userMapper,
                        RoleService roleService) {
                this.authService = authService;
                this.securityService = securityService;
                this.userService = userService;
                this.userMapper = userMapper;
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
        public ResponseEntity<ApiResponse<Object>> login(@Valid @RequestBody LoginReq loginReq,
                        BindingResult bindingResult) throws Exception {

                System.out.println(loginReq);
                if (bindingResult.hasErrors()) {
                        throw new BindException(bindingResult);
                }
                AccountInfoRes accountInfoRes = this.authService.handleLogin(loginReq);

                System.out.println(">>> [INFO] Account Info: " + accountInfoRes.getEmail());

                
                // Create refresh token and update to database
                String access_token = this.securityService.createAccessToken(accountInfoRes.getId(), accountInfoRes);
                String refresh_token = this.securityService.createRefreshToken(accountInfoRes.getId(), accountInfoRes);
                System.out.println(">>> [INFO] Account Info: " + accountInfoRes.getEmail());
                userService.handleUpdateRefreshTokenById(accountInfoRes.getId(), refresh_token);

                LoginRes loginRes = LoginRes.builder()
                                .accessToken(access_token)
                                .accountInfo(accountInfoRes)
                                .build();

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
                                loginRes);

                return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookies.toString()).body(response);
        }

        @PostMapping("/login/google")
        public ResponseEntity<ApiResponse<Object>> loginWithGoogle(
                @Valid @RequestBody SocialLoginReq socialLoginReq, 
                BindingResult bindingResult ) throws Exception {

                if (bindingResult.hasErrors()) {
                        throw new BindException(bindingResult);
                }
               
                User user = this.authService.processOAuthLogin(socialLoginReq.getToken(), AuthProviderEnum.GOOGLE);

                if (user == null) {
                        throw new IdInvaildException("User not found");
                }

                AccountInfoRes accountInfoRes = userMapper.mapToAccountInfoRes(user);

                String access_token = this.securityService.createAccessToken(accountInfoRes.getId(), accountInfoRes);
                String refresh_token = this.securityService.createRefreshToken(accountInfoRes.getId(), accountInfoRes);
                userService.handleUpdateRefreshTokenById(accountInfoRes.getId(), refresh_token);

                // Create LoginRes object
                LoginRes loginRes = LoginRes.builder()
                                .accessToken(access_token)
                                .accountInfo(accountInfoRes)
                                .build();
              
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
                                "Login with Google success",
                                loginRes);
                return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookies.toString()).body(response);

               
        }

        @PostMapping("/login/facebook")
        public ResponseEntity<ApiResponse<Object>> loginWithFacebook(
                @Valid @RequestBody SocialLoginReq socialLoginReq, 
                BindingResult bindingResult) throws Exception {

                if (bindingResult.hasErrors()) {
                        throw new BindException(bindingResult);
                }
                System.out.println(">>> ID Token: " + socialLoginReq.getToken());

                // Create refresh token and update to database
                User user = this.authService.processOAuthLogin(socialLoginReq.getToken(), AuthProviderEnum.FACEBOOK);

                if (user == null) throw new IdInvaildException("User not found");


                AccountInfoRes accountInfoRes = userMapper.mapToAccountInfoRes(user);

                String refresh_token = this.securityService.createRefreshToken(accountInfoRes.getId(), accountInfoRes);
                String access_token = this.securityService.createAccessToken(accountInfoRes.getId(), accountInfoRes);

                userService.handleUpdateRefreshTokenById(accountInfoRes.getId(), refresh_token);

                LoginRes loginRes = LoginRes.builder()
                                .accessToken(access_token)
                                .accountInfo(accountInfoRes)
                                .build();
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
                                "Login with Facebook success",
                                loginRes);
                
                return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookies.toString()).body(response);
        }

        @GetMapping("/account")
        public ResponseEntity<ApiResponse<AccountInfoRes>> account() throws Exception {

                String id = this.securityService.getCurrentUserLogin().isPresent()
                                ? securityService.getCurrentUserLogin().get()
                                : null;

                User user = this.userService.handleGetUserById(Long.valueOf(id));

                if (user == null) {
                        throw new IdInvaildException("User not found");
                }

                AccountInfoRes accountInfoRes = userMapper.mapToAccountInfoRes(user);
                ApiResponse<AccountInfoRes> response = new ApiResponse<>(
                                200,
                                null,
                                "Fetch account",
                                accountInfoRes);
                return ResponseEntity.ok().body(response);
        }

        @GetMapping("/refresh")
        public ResponseEntity<ApiResponse<Object>> refreshToken(
                        @CookieValue(name = "refresh-token") String refreshToken)
                        throws MissingRequestCookieException, Exception {
              
                AccountInfoRes accountInfoRes = this.authService.handleRefreshToken(refreshToken);



                // Create new refresh token
                String new_refresh_token = this.securityService.createRefreshToken(accountInfoRes.getId(), accountInfoRes);
                String access_token = this.securityService.createAccessToken(accountInfoRes.getId(), accountInfoRes);
                        
                // Update new refresh token to database
                this.userService.handleUpdateRefreshTokenById(accountInfoRes.getId(), new_refresh_token);

                LoginRes loginRes = LoginRes.builder()
                                .accessToken(access_token)
                                .accountInfo(accountInfoRes)
                                .build();

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
                                loginRes);

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
