package com.example.sales_manager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import com.example.sales_manager.dto.ReqCreateUserDto;
import com.example.sales_manager.dto.ReqUpdateUserDto;
import com.example.sales_manager.entity.User;
import com.example.sales_manager.exception.DataNotFoundException;
import com.example.sales_manager.exception.RestResponse;
import com.example.sales_manager.service.UserService;
import com.turkraft.springfilter.boot.Filter;

import org.springframework.validation.BindException;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
        
    @GetMapping("/getUsers")
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
    @GetMapping("/getUserById/{id}") 
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) throws Exception{
        RestResponse<Object> response = new RestResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Get user successfully");
        response.setData(userService.handleGetUserById(id));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

   

    @PostMapping("/createUser")
    public ResponseEntity<RestResponse<Object>> addUser(
            @Valid @RequestBody ReqCreateUserDto reqCreateUserDto, 
            @RequestParam("files") MultipartFile files[],
            BindingResult bindingResult) throws Exception {
        
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        RestResponse<Object> response = new RestResponse<>();
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("Create user successfully");
        response.setData(userService.handleCreateUser(reqCreateUserDto, files));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<RestResponse<Object>> updateUser(
            @PathVariable("id") Long id, 
            @Valid @RequestBody ReqUpdateUserDto reqUpdateUserDto, 
            @RequestParam("files") MultipartFile files[],
            BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        RestResponse<Object> response = new RestResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Update user successfully");
        response.setData(userService.handleUpdateUser(id, reqUpdateUserDto, files));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/deleteUser/{id}")
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
