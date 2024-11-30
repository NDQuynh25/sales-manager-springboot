package com.example.sales_manager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import com.example.sales_manager.dto.request.ReqCreateUserDto;
import com.example.sales_manager.dto.request.ReqUpdateUserDto;
import com.example.sales_manager.dto.response.RestResponse;
import com.example.sales_manager.entity.User;
import com.example.sales_manager.exception.DataNotFoundException;
import com.example.sales_manager.service.UserService;
import com.turkraft.springfilter.boot.Filter;

import org.springframework.validation.BindException;





@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('USER_READ')")
    @GetMapping("/getAll")
    public ResponseEntity<RestResponse<Object>> getUsers( 
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @Filter Specification<User> spec,
            Sort sort) throws Exception, DataNotFoundException{
        
        if (sort == null) {
            sort = Sort.by(Sort.Order.asc("fullName")); // Sort by 'fullName' in ascending order
        }
        // Create pageable
        Pageable pageable = PageRequest.of(page, size, sort);    
        // Create response
        RestResponse<Object> response = new RestResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Get users successfully");
        response.setData(userService.handleGetUsers(spec, pageable));
        // Return response
        return ResponseEntity.status(HttpStatus.OK).body(response);
        
    }

    @PreAuthorize("#id == principal.claims['user']['id'] or (hasRole('ROLE_ADMIN') and hasAuthority('USER_READ'))")
    @GetMapping("/getById/{id}") 
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) throws Exception{
        RestResponse<Object> response = new RestResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Get user successfully");
        response.setData(userService.handleGetUserById(id));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

   
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('USER_CREATE')")
    @PostMapping("/create")
    public ResponseEntity<RestResponse<Object>> addUser(
            @ModelAttribute ReqCreateUserDto reqCreateUserDto, 
            BindingResult bindingResult) throws Exception {
        
        // Debugging the user data to see the contents of ReqCreateUserDto
        System.out.println(">>> User Data: " + reqCreateUserDto.getAvatarFile());

        
        // Process the user creation
        RestResponse<Object> response = new RestResponse<>();
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("Create user successfully");
        response.setData(userService.handleCreateUser(reqCreateUserDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("#id == principal.claims['user']['id'] or (hasRole('ROLE_ADMIN') and hasAuthority('USER_UPDATE'))")
    @PutMapping("/update/{id}")
    public ResponseEntity<RestResponse<Object>> updateUser(
            @Valid @ModelAttribute ReqUpdateUserDto reqUpdateUserDto, 
            BindingResult bindingResult) throws Exception {

                
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        System.out.println(">>> Data update user: " + reqUpdateUserDto.getId());
        RestResponse<Object> response = new RestResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Update user successfully");
        response.setData(userService.handleUpdateUser(reqUpdateUserDto));
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('USER_DELETE')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<RestResponse<Object>> deleteUser(@PathVariable("id") Long id) throws Exception {
        if (userService.handleDeleteUserById(id)) {
            RestResponse<Object> response = new RestResponse<>();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Delete user successfully");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return null;



        
        
    }
    


  
}
