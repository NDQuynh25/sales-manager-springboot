package com.example.sales_manager.service;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;

import com.example.sales_manager.dto.request.LoginReq;
import com.example.sales_manager.dto.request.RegisterReq;
import com.example.sales_manager.dto.response.LoginRes;
import com.example.sales_manager.entity.Role;
import com.example.sales_manager.entity.User;
import com.example.sales_manager.exception.IdInvaildException;
import com.example.sales_manager.repository.UserRepository;

@Service
public class AuthService {
    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private final UserService userService;

    private final RoleService roleService;

    private final SecurityService securityService;

    private final UserRepository userRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthService(
            UserService userService,
            RoleService roleService,
            UserRepository userRepository,
            AuthenticationManagerBuilder authenticationManagerBuilder,
            SecurityService securityService) {

        this.userService = userService;
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityService = securityService;
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
    public LoginRes handleLogin(LoginReq LoginReq) throws Exception {

        // Nạp username và password vào security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                LoginReq.getEmail(), LoginReq.getPassword());

        // Xác thực => loadUserByUsername trong
        Authentication authentication = this.authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // Lưu thông tin xác thực vào SecurityContextHolder để sử dụng sau này
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Create response
        LoginRes LoginRes = new LoginRes();
        LoginRes.User userDto = LoginRes.new User();

        User user = this.userService.handleGetUserByEmail(LoginReq.getEmail());

        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        userDto.setAvatar(user.getAvatar());

        userDto.setRole(this.roleService.mapRoleToRoleDto(this.roleService.handleGetRoleById(user.getRole().getId())));
        LoginRes.setUser(userDto);

        // Create access token
        String access_token = this.securityService.createAccessToken(user.getEmail(), LoginRes);

        LoginRes.setAccessToken(access_token);
        return LoginRes;
    }

    // Handle logout
    public void handleLogout() throws Exception {
        // Get the email of the current user
        String email = this.securityService.getCurrentUserLogin().isPresent()
                ? securityService.getCurrentUserLogin().get()
                : null;
        if (email == null) {
            throw new IdInvaildException("Email is invalid");
        }

        // Delete information about the current user in SecurityContextHolder
        SecurityContextHolder.clearContext();

        // Delete the refresh token in the database
        this.userService.handleUpdateRefreshTokenByEmail(email, null);

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
    public LoginRes handleRefreshToken(String refreshToken) throws Exception {
        System.out.println("handled refresh token");
        // Kiểm tra tính hợp lệ của token làm mới và giải mã thông tin
        Jwt decodedToken = this.securityService.checkValidRefreshToken(refreshToken);
        String email = decodedToken.getSubject();

        // Kiểm tra tính hợp lệ của email và token làm mới
        User user = this.userService.handleGetUserByEmailAndRefreshToken(email, refreshToken);
        if (user == null) {
            throw new IdInvaildException("Email or refresh token is invalid");

        }

        // Tạo đối tượng LoginRes để chứa thông tin người dùng và token truy cập mới
        LoginRes LoginRes = new LoginRes();

        // Tạo đối tượng UserDto để chứa thông tin người dùng
        LoginRes.User userDto = LoginRes.new User();
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(this.roleService.mapRoleToRoleDto(this.roleService.handleGetRoleById(user.getRole().getId())));
        LoginRes.setUser(userDto);

        // Tạo token truy cập mới và gán vào đối tượng LoginRes
        String accessToken = this.securityService.createAccessToken(user.getEmail(), LoginRes);
        LoginRes.setAccessToken(accessToken);

        return LoginRes;
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
}
