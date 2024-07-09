package com.example.sales_manager.service;

import org.springframework.stereotype.Service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.sales_manager.dto.ReqLoginDto;
import com.example.sales_manager.dto.RegisterDto;
import com.example.sales_manager.dto.ResLoginDto;
import com.example.sales_manager.entity.User;
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

    public boolean handleRegister(RegisterDto registerDto) {
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
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // Create response
        ResLoginDto resLoginDto = new ResLoginDto();
        ResLoginDto.User user = resLoginDto.new User();

        User userx = userService.handleGetUserByEmail(reqLoginDto.getEmail());

        user.setId(userx.getId());
        user.setFullName(userx.getFullName());
        user.setEmail(userx.getEmail());
        user.setRoleId(userx.getRoleId());

        resLoginDto.setUser(user);

        String access_token = securityService.createAccessToken(authentication, resLoginDto);

        resLoginDto.setAccessToken(access_token);

        
        // Create access token
        
        SecurityContextHolder.getContext().setAuthentication(authentication);


        

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

    public User mapRegisterDtoToUser(RegisterDto registerDto) {
        User user = new User();
        user.setFullName(registerDto.getFullname());
        user.setEmail(registerDto.getEmail());
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setPassword(registerDto.getPassword());
        return user;
    }
}
