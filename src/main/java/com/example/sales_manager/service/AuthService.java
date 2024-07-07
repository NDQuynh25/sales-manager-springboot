package com.example.sales_manager.service;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.sales_manager.dto.LoginDto;
import com.example.sales_manager.dto.RegisterDto;
import com.example.sales_manager.entity.User;
import com.example.sales_manager.repository.UserRepository;


@Service
public class AuthService {
    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private final UserService userService;

    private final UserRepository userRepository;

    public AuthService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
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
