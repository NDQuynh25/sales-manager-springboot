package com.example.sales_manager.service;

import org.springframework.stereotype.Service;

import java.security.AuthProvider;
import java.util.Set;
import java.util.stream.Collectors;

import org.checkerframework.checker.units.qual.g;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;

import com.example.sales_manager.dto.OAuth2UserInfo;
import com.example.sales_manager.dto.request.LoginReq;
import com.example.sales_manager.dto.request.RegisterReq;
import com.example.sales_manager.dto.response.AccountInfoRes;
import com.example.sales_manager.dto.response.LoginRes;
import com.example.sales_manager.entity.Cart;
import com.example.sales_manager.entity.Role;
import com.example.sales_manager.entity.User;
import com.example.sales_manager.exception.IdInvaildException;
import com.example.sales_manager.repository.UserRepository;
import com.example.sales_manager.util.constant.AuthProviderEnum;

import lombok.extern.java.Log;

import com.example.sales_manager.mapper.UserMapper;




@Service
public class AuthService {
    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private final UserService userService;

    private final RoleService roleService;

    private final SecurityService securityService;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final OAuth2UserService oAuth2UserService;

   


    public AuthService(
            UserService userService,
            RoleService roleService,
            UserRepository userRepository,
            UserMapper userMapper,
            AuthenticationManagerBuilder authenticationManagerBuilder,
            SecurityService securityService,
            OAuth2UserService oAuth2UserService) {

        this.userService = userService;
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityService = securityService;
        this.oAuth2UserService = oAuth2UserService;
    }

    public boolean handleRegister(RegisterReq registerDto) {
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

    // Handle login
    // Handle login
    public AccountInfoRes handleLogin(LoginReq LoginReq) throws Exception {
        // Nạp username và password vào security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                LoginReq.getEmail(), LoginReq.getPassword());

        // Xác thực => loadUserByUsername trong
        Authentication authentication = this.authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // Lưu thông tin xác thực vào SecurityContextHolder để sử dụng sau này
        SecurityContextHolder.getContext().setAuthentication(authentication);


        User user = userService.handleGetUserByEmail(LoginReq.getEmail());
        if (user == null) {
            throw new IdInvaildException("Email is invalid");
        }

        return userMapper.mapToAccountInfoRes(user);
    }

    // Handle logout
    public void handleLogout() throws Exception {
        // Get the email of the current user
        String id = this.securityService.getCurrentUserLogin().isPresent()
                ? securityService.getCurrentUserLogin().get()
                : null;
        System.out.println(">>> [INFO] Logging out user with ID: " + id);
        if (id == null) {
            throw new IdInvaildException("Email is invalid");
        }

        // Delete information about the current user in SecurityContextHolder
        SecurityContextHolder.clearContext();

        // Delete the refresh token in the database
        this.userService.handleUpdateRefreshTokenById(Long.parseLong(id), null);

    }

    // Handle refresh token (create new access token)
    /**
     * Xử lý token làm mới và trả về thông tin đăng nhập mới.
     * 
     * @param refreshToken Token làm mới được gửi từ phía khách hàng
     * @return Đối tượng LoginRes chứa thông tin người dùng và token truy cập mới
     * @throws Exception Nếu có lỗi xảy ra khi xử lý token hoặc lấy dữ liệu người
     *                   dùng
     */
    public AccountInfoRes handleRefreshToken(String refreshToken) throws Exception {
        System.out.println("handled refresh token");
        // Kiểm tra tính hợp lệ của token làm mới và giải mã thông tin
        Jwt decodedToken = this.securityService.checkValidRefreshToken(refreshToken);
        Long userId = Long.parseLong(decodedToken.getSubject());

        // Kiểm tra tính hợp lệ của email và token làm mới
        User user = this.userService.handleGetUserByIdAndRefreshToken(userId, refreshToken);
        if (user == null) {
            throw new IdInvaildException("Email or refresh token is invalid");

        }
        return userMapper.mapToAccountInfoRes(user);
    }

    /**
     * Lấy tên của role dựa trên roleId.
     * 
     * @param roleId ID của role
     * @return Tên của role
     * @throws Exception Nếu có lỗi xảy ra khi lấy dữ liệu
     */
    public String handleGetRoleNameById(Long roleId) throws Exception {
        // Lấy đối tượng Role từ RoleService
        Role role = this.roleService.handleGetRoleById(roleId);

        // Trả về tên của Role
        return role.getRoleName();
    }

    /**
     * Lấy danh sách các quyền dựa trên roleId.
     * 
     * @param roleId ID của role
     * @return Set chứa các tên quyền của role
     * @throws Exception Nếu có lỗi xảy ra khi lấy dữ liệu
     */
    public Set<String> handleGetPermissionsByRoleId(Long roleId) throws Exception {
        // Lấy đối tượng Role từ RoleService
        Role role = this.roleService.handleGetRoleById(roleId);

        // Lấy danh sách các quyền từ Role và chuyển đổi thành Set<String>
        Set<String> permissions = role.getPermissions()
                .stream()
                .map(item -> item.getPermissionName()) // Lấy tên của quyền
                .collect(Collectors.toSet()); // Tập hợp các tên quyền vào Set

        // Trả về danh sách các quyền
        return permissions;
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

    public User mapRegisterDtoToUser(RegisterReq registerDto) {
        User user = new User();
        user.setFullName(registerDto.getFullname());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        return user;
    }

    public User processOAuthLogin(String token, AuthProviderEnum provider) {
        try {
            if (provider == AuthProviderEnum.GOOGLE) {
            
                OAuth2UserInfo userInfo = oAuth2UserService.verifyGoogleToken(token);
                return handleGetOrCreateUser(userInfo, AuthProviderEnum.GOOGLE);
            
            } else if (provider == AuthProviderEnum.FACEBOOK) {
                OAuth2UserInfo userInfo = oAuth2UserService.verifyFacebookToken(token);
                return handleGetOrCreateUser(userInfo, AuthProviderEnum.FACEBOOK);
            } else {
                throw new BadCredentialsException("Unsupported authentication provider");
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Error processing OAuth login: " + e.getMessage());
            throw new BadCredentialsException("Failed to process OAuth login");
        }
    }

    private User handleGetOrCreateUser(OAuth2UserInfo userInfo, AuthProviderEnum provider) throws Exception {
        try {
            
            if (userInfo == null || userInfo.getId() == null) {
                throw new BadCredentialsException("Invalid OAuth2 user info");
            } else {
                if (provider == AuthProviderEnum.GOOGLE) {
                    User existingUser = userRepository.findByGoogleAccountId(userInfo.getId());
                    if (existingUser != null) {
                        return existingUser; // Return existing user if found
                    }
                } else if (provider == AuthProviderEnum.FACEBOOK) {
                    User existingUser = userRepository.findByFacebookAccountId(userInfo.getId());
                    if (existingUser != null) {
                        return existingUser; // Return existing user if found
                    }
                }
            }

            if (userInfo.getEmail() != null && !userInfo.getEmail().isEmpty()) {
                
                User existingUser = userRepository.findByEmail(userInfo.getEmail());
                if (existingUser != null && existingUser.getAuthProvider() != provider) {
                    throw new BadCredentialsException("User already exists with this email and provider");
                }
            }
          
            // Create new user if not exists
            User newUser = new User();
            if (provider == AuthProviderEnum.GOOGLE) {
                newUser.setGoogleAccountId(userInfo.getId());

            } else if (provider == AuthProviderEnum.FACEBOOK) {
                newUser.setFacebookAccountId(userInfo.getId());
            }
            Cart cart = new Cart();
            newUser.setCart(cart);
            newUser.setFullName(userInfo.getName());
            newUser.setEmail(userInfo.getEmail());
            newUser.setAvatar(userInfo.getPicture());
            newUser.setAuthProvider(provider);
            newUser.setRole(roleService.handleGetDefaultRole());
            

            return userRepository.save(newUser);
        } catch (Exception e) {
            System.err.println("Error while processing OAuth login: " + e.getMessage());
            throw new BadCredentialsException("Failed to process OAuth login");
        }
       
        
    }

    // public void loginWithFacebook(String accessToken) {
    //     try {
    //         oAuth2UserService.verifyFacebookToken(accessToken);
            
    //     } catch (Exception e) {
    //         System.err.println("Error verifying Facebook access token: " + e.getMessage());
    //         // Handle error, e.g., throw a custom exception or return an error response
    //     }
    // }
}
