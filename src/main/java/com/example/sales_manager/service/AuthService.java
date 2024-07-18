package com.example.sales_manager.service;

import org.springframework.stereotype.Service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;

import com.example.sales_manager.dto.request.ReqLoginDto;
import com.example.sales_manager.dto.request.ReqRegisterDto;
import com.example.sales_manager.dto.response.ResLoginDto;
import com.example.sales_manager.entity.User;
import com.example.sales_manager.exception.IdInvaildException;
import com.example.sales_manager.repository.UserRepository;


@Service
public class AuthService {
    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private final UserService userService;

    private final SecurityService securityService;

    private final UserRepository userRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthService(
            UserService userService, 
            UserRepository userRepository, 
            AuthenticationManagerBuilder authenticationManagerBuilder,
            SecurityService securityService) {

        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityService = securityService;
    }

    public boolean handleRegister(ReqRegisterDto registerDto) {
        try {
            User user = this.mapRegisterDtoToUser(registerDto);
            user.setPassword(handleHashPassword(user.getPassword()));
            userRepository.save(user);
            return true;
            
        } catch (Exception e) {
            System.err.println("Error while registering user with email");
            return false;
            // Xử lý lỗi khi thêm mới user (ví dụ: ghi log hoặc thông báo cho người dùng)
        }
    }

    public ResLoginDto handleLogin(ReqLoginDto reqLoginDto) throws Exception{

        // Nạp username và password vào security 
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(reqLoginDto.getEmail(), reqLoginDto.getPassword());
        
        // Xác thực => loadUserByUsername trong
        Authentication authentication = this.authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // Lưu thông tin xác thực vào SecurityContextHolder để sử dụng sau này
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Create response
        ResLoginDto resLoginDto = new ResLoginDto();
        ResLoginDto.User user = resLoginDto.new User();

        User userx = this.userService.handleGetUserByEmail(reqLoginDto.getEmail());

        user.setId(userx.getId());
        user.setFullName(userx.getFullName());
        user.setEmail(userx.getEmail());
        user.setRoleId(userx.getRoleId());

        resLoginDto.setUser(user);

        // Create access token
        String access_token = this.securityService.createAccessToken(userx.getEmail(), resLoginDto);

        resLoginDto.setAccessToken(access_token);
        return resLoginDto;
    }

    public void handleLogout() throws Exception{
        // Get the email of the current user
        String email = this.securityService.getCurrentUserLogin().isPresent() ? securityService.getCurrentUserLogin().get() : null;
        if (email == null) {
            throw new IdInvaildException("Email is invalid");
        }
        
        // Delete information about the current user in SecurityContextHolder
        SecurityContextHolder.clearContext();

        // Delete the refresh token in the database
        this.userService.handleUpdateRefreshTokenByEmail(email, null);

    }

    public ResLoginDto handleRefreshToken (String refreshToken) throws Exception{
        Jwt decodedToken = this.securityService.checkValidRefreshToken(refreshToken);
        String email = decodedToken.getSubject();

        // check email and refresh token
        User user = this.userService.handleGetUserByEmailAndRefreshToken(email, refreshToken);
        if (user == null) {
            throw new IdInvaildException("Email or refresh token is invalid");
        }

        // Create new access token
        ResLoginDto resLoginDto = new ResLoginDto();
        ResLoginDto.User userDto = resLoginDto.new User(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getRoleId()
        );
        resLoginDto.setUser(userDto);

        String access_token = this.securityService.createAccessToken(user.getEmail(), resLoginDto);
        resLoginDto.setAccessToken(access_token);

        return resLoginDto;
    }
    

  





















    public String handleHashPassword(String password) {
        try {
            return bCryptPasswordEncoder.encode(password); 

        } catch (Exception e) {
            System.err.println("Error while hashing password: " + e);
            return null;
        }
    }

    public Boolean handleCheckPassword(String password, String hashedPassword) {
        try {
            return bCryptPasswordEncoder.matches(password, hashedPassword);

        } catch (Exception e) {
            System.err.println("Error while checking password: " + e);
            return false;
        }
    }

    public User mapRegisterDtoToUser(ReqRegisterDto registerDto) {
        User user = new User();
        user.setFullName(registerDto.getFullname());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        return user;
    }
}
