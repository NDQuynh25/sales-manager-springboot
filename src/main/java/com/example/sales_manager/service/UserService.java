package com.example.sales_manager.service;

import com.example.sales_manager.dto.UserDto;
import com.example.sales_manager.entity.User;
import com.example.sales_manager.exception.AccountNotFoundException;
import com.example.sales_manager.exception.DataIntegrityViolationException;
import com.example.sales_manager.exception.IdInvaildException;
import com.example.sales_manager.repository.UserRepository;
import org.springframework.validation.BindException;

import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;
import org.springframework.stereotype.Service;

    /*  
    @Transactional ensures that the entire method runs in one transaction. 
    If any exception occurs, the transaction will be rolled back to ensure 
    data consistency
    */
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    // Dependency Injection (DI) to inject UserRepository
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleAddUser(UserDto userDto) throws Exception{
        
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new DataIntegrityViolationException("User with email " + userDto.getEmail() + " already exists!");
        }
        if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            throw new DataIntegrityViolationException("User with phone number " + userDto.getPhoneNumber() + " already exists!");
        }
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new Exception("Password and confirm password do not match!");
        }
        User user = new User();
        user.setFullname(userDto.getFullname());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(userDto.getPassword());
        user.setRoleId(userDto.getRoleId());
        user.setAddress(userDto.getAddress());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setFacebookAccountId(userDto.getFacebookAccountId());
        user.setGoogleAccountId(userDto.getGoogleAccountId());
        return userRepository.save(user);

    }
    public List<User> handleGetAllUsers() throws Exception{
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public User handleGetUserById(Long id) throws Exception {

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new IdInvaildException("User with id " + id + " does not exist!");
        }
        return user;
        
    }

    public User handleGetUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new AccountNotFoundException("Account not found!");
        }
        return user;
    }

    public User handleUpdateUser(User user) {
        try {
            User existingUser = userRepository.findById(user.getId()).orElse(null);
            if (existingUser == null) {
                throw new Exception("User with id " + user.getId() + " does not exist!");
            }
            existingUser.setFullname(user.getFullname());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            // existingUser.setPassword(user.getPassword());
            existingUser.setRoleId(user.getRoleId());
            existingUser.setAddress(user.getAddress());
            existingUser.setDateOfBirth(user.getDateOfBirth());
            existingUser.setFacebookAccountId(user.getFacebookAccountId());
            existingUser.setGoogleAccountId(user.getGoogleAccountId());
            return userRepository.save(existingUser);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean handleDeleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    


}