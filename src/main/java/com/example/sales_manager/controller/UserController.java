package com.example.sales_manager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import com.example.sales_manager.dto.UserDto;
import com.example.sales_manager.exception.RestResponse;
import com.example.sales_manager.service.UserService;
import org.springframework.validation.BindException;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
        
    @GetMapping("/getAllUsers")
    public ResponseEntity<RestResponse<Object>> getAllUsers() throws Exception{
        RestResponse<Object> response = new RestResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Get all users successfully");
        response.setData(userService.handleGetAllUsers());
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

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) throws Exception {
        
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.handleAddUser(userDto));
        
    }

  
}
