package com.example.sales_manager.service;

import com.example.sales_manager.dto.ResultPagination;
import com.example.sales_manager.dto.request.CreateUserReq;
import com.example.sales_manager.dto.request.UpdateUserReq;
import com.example.sales_manager.dto.response.RoleRes;
import com.example.sales_manager.dto.response.UserRes;
import com.example.sales_manager.entity.Cart;
import com.example.sales_manager.entity.Role;
import com.example.sales_manager.entity.User;
import com.example.sales_manager.exception.DataIntegrityViolationException;
import com.example.sales_manager.exception.IdInvaildException;
import com.example.sales_manager.repository.UserRepository;

import jakarta.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/*  
@Transactional ensures that the entire method runs in one transaction. 
If any exception occurs, the transaction will be rolled back to ensure 
data consistency
*/

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;
    private final EntityManager entityManager;
    private final RoleService roleService;

    // Dependency Injection (DI) to inject UserRepository
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EntityManager entityManager,
            FileService fileService, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityManager = entityManager;
        this.fileService = fileService;
        this.roleService = roleService;
    }

    // Method to handle adding a new user
    public User handleCreateUser(CreateUserReq CreateUserReq) throws Exception {

        if (userRepository.existsByEmail(CreateUserReq.getEmail())) {
            throw new DataIntegrityViolationException(
                    "User with email " + CreateUserReq.getEmail() + " already exists!");
        }
        if (userRepository.existsByPhoneNumber(CreateUserReq.getPhoneNumber())) {
            throw new DataIntegrityViolationException(
                    "User with phone number " + CreateUserReq.getPhoneNumber() + " already exists!");
        }
        if (!CreateUserReq.getPassword().equals(CreateUserReq.getConfirmPassword())) {
            throw new Exception("Password and confirm password do not match!");
        }

        String urlsImageString = null;
        MultipartFile file = CreateUserReq.getAvatarFile(); // Get avatar
        if (file != null && file.isEmpty()) {
            urlsImageString = fileService.handleUploadFile(file); // Upload avatar
        }

        // create Cart object
        Cart cart = new Cart();

        User user = new User();
        user.setFullName(CreateUserReq.getFullName());
        user.setEmail(CreateUserReq.getEmail());
        user.setPhoneNumber(CreateUserReq.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(CreateUserReq.getPassword()));
        user.setGender(CreateUserReq.getGender());
        user.setAddress(CreateUserReq.getAddress());
        user.setAvatar(urlsImageString);
        user.setDateOfBirth(CreateUserReq.getDateOfBirth());
        user.setFacebookAccountId(CreateUserReq.getFacebookAccountId());
        user.setGoogleAccountId(CreateUserReq.getGoogleAccountId());

        // Set role
        Role role = new Role();
        role = roleService.handleGetRoleById(CreateUserReq.getRoleId());
        user.setRole(role);

        // Set cart

        return userRepository.save(user);

    }

    // Method to handle getting users
    public ResultPagination handleGetUsers(Specification<User> spec, Pageable pageable) throws Exception {
        Page<User> page = userRepository.findAll(spec, pageable);
        List<UserRes> users = page.getContent().stream().map(item -> this.mapUserToUserRes(item)).toList();

        ResultPagination resultPagination = new ResultPagination();
        ResultPagination.Meta meta = new ResultPagination.Meta();

        meta.setPage(page.getNumber());
        meta.setPageSize(page.getSize());
        meta.setTotalPages(page.getTotalPages());
        meta.setTotalElements(page.getTotalElements());

        resultPagination.setMeta(meta);
        resultPagination.setResult(users);

        return resultPagination;

    }

    // Method to handle getting a user by id
    public User handleGetUserById(Long id) throws Exception {

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new IdInvaildException("User with id " + id + " does not exist!");
        }
        return user;
    }

    // Method to handle getting a user by email
    public User handleGetUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(null);
        }
        return user;
    }

    // Method to handle updating a user
    @Transactional
    public UserRes handleUpdateUser(UpdateUserReq UpdateUserReq) throws Exception {

        User existingUser = userRepository.findById(UpdateUserReq.getId()).orElse(null);
        if (existingUser == null) {
            throw new Exception("User with id " + UpdateUserReq.getId() + " does not exist!");
        }

        MultipartFile file = UpdateUserReq.getAvatarFile(); // Get avatar
        String urlImageString;
        if (file != null) {
            urlImageString = fileService.handleUploadFile(file); // Upload avatar
        } else {
            urlImageString = existingUser.getAvatar();
        }

        existingUser.setFullName(UpdateUserReq.getFullName());
        existingUser.setPhoneNumber(UpdateUserReq.getPhoneNumber());
        existingUser.setGender(UpdateUserReq.getGender());
        existingUser.setIsActive(UpdateUserReq.getIsActive());
        // existingUser.setRoleId(UpdateUserReq.getRoleId());
        existingUser.setAvatar(urlImageString);
        existingUser.setAddress(UpdateUserReq.getAddress());
        existingUser.setDateOfBirth(UpdateUserReq.getDateOfBirth());
        User user = userRepository.save(existingUser);
        entityManager.flush(); // Đảm bảo các thay đổi được đẩy xuống cơ sở dữ liệu
        return mapUserToUserRes(user);

    }

    // Method to handle deleting a user by id
    public boolean handleDeleteUserById(Long id) throws Exception {

        boolean user = userRepository.existsById(id);
        if (user == false) {
            throw new Exception("User with id " + id + " does not exist!");
        }
        userRepository.deleteById(id);

        return true;

    }

    // Method to handle updating refresh token by email
    public void handleUpdateRefreshTokenById(Long id, String refreshToken) throws Exception {
        User user = userRepository.findByIdAndIsActive(id, 1);
        if (user == null) {
            System.out.println(">>> [ERROR] User with id " + id + " does not exist or is inactive! (UserService.handleUpdateRefreshTokenById)");
            throw new UsernameNotFoundException("User with id " + id + " does not exist or is inactive!");
        }
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }

   

    // Method to handle getting a user by email and refresh token
    public User handleGetUserByIdAndRefreshToken(Long id, String refreshToken) throws Exception {
        User user = userRepository.findByIdAndRefreshTokenAndIsActive(id, refreshToken, 1);
        return user;
    }

    // Method mapper to map the user entity to response user dto
    public UserRes mapUserToUserRes(User user) {
        UserRes UserRes = new UserRes();
        UserRes.setId(user.getId());
        UserRes.setFullName(user.getFullName());
        UserRes.setEmail(user.getEmail());
        UserRes.setPhoneNumber(user.getPhoneNumber());
        UserRes.setGender(user.getGender());
        UserRes.setDateOfBirth(user.getDateOfBirth());

        RoleRes RoleRes = new RoleRes();
        try {
            RoleRes = roleService.mapRoleToRoleRes(roleService.handleGetRoleById(user.getRole().getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserRes.setIsActive(user.getIsActive());
        UserRes.setRole(RoleRes);
        UserRes.setAddress(user.getAddress());
        UserRes.setAvatar(user.getAvatar());
        UserRes.setDateOfBirth(user.getDateOfBirth());
        UserRes.setCreatedAt(user.getCreatedAt());
        UserRes.setUpdatedAt(user.getUpdatedAt());
        UserRes.setCreatedBy(user.getCreatedBy());
        UserRes.setUpdatedBy(user.getUpdatedBy());

        return UserRes;
    }

}
