package com.example.sales_manager.service;

import com.example.sales_manager.dto.ResultPagination;
import com.example.sales_manager.dto.request.ReqCreateUserDto;
import com.example.sales_manager.dto.request.ReqUpdateUserDto;
import com.example.sales_manager.dto.response.ResRoleDto;
import com.example.sales_manager.dto.response.ResUserDto;
import com.example.sales_manager.entity.Cart;
import com.example.sales_manager.entity.Role;
import com.example.sales_manager.entity.User;
import com.example.sales_manager.exception.DataIntegrityViolationException;
import com.example.sales_manager.exception.DataNotFoundException;
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
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EntityManager entityManager, FileService fileService, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityManager = entityManager;
        this.fileService = fileService;
        this.roleService = roleService;
    }

    // Method to handle adding a new user
    public User handleCreateUser(ReqCreateUserDto reqCreateUserDto) throws Exception{
        
        if (userRepository.existsByEmail(reqCreateUserDto.getEmail())) {
            throw new DataIntegrityViolationException("User with email " + reqCreateUserDto.getEmail() + " already exists!");
        }
        if (userRepository.existsByPhoneNumber(reqCreateUserDto.getPhoneNumber())) {
            throw new DataIntegrityViolationException("User with phone number " + reqCreateUserDto.getPhoneNumber() + " already exists!");
        }
        if (!reqCreateUserDto.getPassword().equals(reqCreateUserDto.getConfirmPassword())) {
            throw new Exception("Password and confirm password do not match!");
        }

        MultipartFile file = reqCreateUserDto.getAvatarFile(); // Get avatar
        String urlsImageString = fileService.handleUploadFile(file); // Upload avatar

        // create Cart object
        Cart cart = new Cart();


        User user = new User();
        user.setFullName(reqCreateUserDto.getFullName());
        user.setEmail(reqCreateUserDto.getEmail());
        user.setPhoneNumber(reqCreateUserDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(reqCreateUserDto.getPassword()));
        user.setGender(reqCreateUserDto.getGender());
        user.setAddress(reqCreateUserDto.getAddress());
        user.setAvatar(urlsImageString);
        user.setDateOfBirth(reqCreateUserDto.getDateOfBirth());
        user.setFacebookAccountId(reqCreateUserDto.getFacebookAccountId());
        user.setGoogleAccountId(reqCreateUserDto.getGoogleAccountId());

        // Set role
        Role role = new Role();
        role = roleService.handleGetRoleById(reqCreateUserDto.getRoleId());
        user.setRole(role);

        // Set cart

        return userRepository.save(user);

      


    }

    // Method to handle getting users
    public ResultPagination handleGetUsers(Specification<User> spec, Pageable pageable) throws Exception{
        Page<User> page = userRepository.findAll(spec, pageable);
        List<ResUserDto> users = page.getContent().stream().map(item -> this.mapUserToResUserDto(item)).toList();
        if (users.isEmpty()) {
            throw new DataNotFoundException("Users information not found!");
        }

        ResultPagination resultPagination = new ResultPagination();
        ResultPagination.Meta meta = resultPagination.new Meta();

        meta.setPage(page.getNumber());
        meta.setPageSize(page.getSize());
        meta.setTotalPages(page.getTotalPages());
        meta.setTotalElements(page.getTotalElements());

        resultPagination.setMeta(meta);
        resultPagination.setResult(users);
      
        return resultPagination;

       
    }

    // Method to handle getting a user by id
    public ResUserDto handleGetUserById(Long id) throws Exception {

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new IdInvaildException("User with id " + id + " does not exist!");
        }
        return this.mapUserToResUserDto(user);
    }

    // Method to handle getting a user by email
    public User handleGetUserByEmail(String email) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(null);
        }
        return user;
    }

    // Method to handle updating a user
    @Transactional
    public ResUserDto handleUpdateUser(ReqUpdateUserDto reqUpdateUserDto) throws Exception {
       
        User existingUser = userRepository.findById(reqUpdateUserDto.getId()).orElse(null);
        if (existingUser == null) {
            throw new Exception("User with id " + reqUpdateUserDto.getId() + " does not exist!");
        }
        
        MultipartFile file = reqUpdateUserDto.getAvatarFile(); // Get avatar
        String urlImageString;
        if (file != null) {
            urlImageString = fileService.handleUploadFile(file); // Upload avatar
        } else {
            urlImageString = existingUser.getAvatar();
        }
       

        existingUser.setFullName(reqUpdateUserDto.getFullName());
        existingUser.setPhoneNumber(reqUpdateUserDto.getPhoneNumber());
        existingUser.setGender(reqUpdateUserDto.getGender());
        existingUser.setIsActive(reqUpdateUserDto.getIsActive());
        //existingUser.setRoleId(reqUpdateUserDto.getRoleId());
        existingUser.setAvatar(urlImageString);
        existingUser.setAddress(reqUpdateUserDto.getAddress());
        existingUser.setDateOfBirth(reqUpdateUserDto.getDateOfBirth());
        User user = userRepository.save(existingUser);
        entityManager.flush(); // Đảm bảo các thay đổi được đẩy xuống cơ sở dữ liệu
        return mapUserToResUserDto(user);

        
    }
    // Method to handle deleting a user by id
    public boolean handleDeleteUserById(Long id) throws Exception{
       
        boolean user = userRepository.existsById(id);
        if (user == false) {
            throw new Exception("User with id " + id + " does not exist!");
        }
        userRepository.deleteById(id);
        return true;
       
    }

    // Method to handle updating refresh token by email
    public void handleUpdateRefreshTokenByEmail(String email, String refreshToken) throws Exception
    {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(null);
        }
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }

    // Method to handle getting a user by email and refresh token
    public User handleGetUserByEmailAndRefreshToken(String email, String refreshToken) throws Exception
    {
        User user = userRepository.findByEmailAndRefreshToken(email, refreshToken);
        return user;
    }



    // Method mapper to map the user entity to response user dto
    public ResUserDto mapUserToResUserDto (User user) {
        ResUserDto resUserDto = new ResUserDto();
        resUserDto.setId(user.getId());
        resUserDto.setFullName(user.getFullName());
        resUserDto.setEmail(user.getEmail());
        resUserDto.setPhoneNumber(user.getPhoneNumber());
        resUserDto.setGender(user.getGender());
        resUserDto.setDateOfBirth(user.getDateOfBirth());

        ResRoleDto resRoleDto = new ResRoleDto();
        try {
            resRoleDto = roleService.mapRoleToResRoleDto(roleService.handleGetRoleById(user.getRole().getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        resUserDto.setIsActive(user.getIsActive());
        resUserDto.setRole(resRoleDto);
        resUserDto.setAddress(user.getAddress());
        resUserDto.setAvatar(user.getAvatar());
        resUserDto.setDateOfBirth(user.getDateOfBirth());
        resUserDto.setCreatedAt(user.getCreatedAt());
        resUserDto.setUpdatedAt(user.getUpdatedAt());
        resUserDto.setCreatedBy(user.getCreatedBy());
        resUserDto.setUpdatedBy(user.getUpdatedBy());

        return resUserDto;
    }

}
    

