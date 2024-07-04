package com.example.sales_manager.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.sales_manager.entity.User;
import com.example.sales_manager.exception.AccountNotFoundException;



@Component("userDetailService")
public class UserDetailService implements UserDetailsService{

    private final UserService userService;

    public UserDetailService(UserService userService) {
        this.userService = userService;
    }

        /**
     * Xác định người dùng dựa trên tên đăng nhập. Trong triển khai thực tế, việc tìm kiếm
     * có thể phân biệt chữ hoa chữ thường hoặc không, tùy thuộc vào cách cấu hình của
     * phiên bản triển khai. Trong trường hợp này, đối tượng <code>UserDetails</code>
     * trả về có thể có tên đăng nhập khác biệt về chữ hoa chữ thường so với tên đăng nhập
     * đã yêu cầu.
     * 
     * @param username tên đăng nhập xác định người dùng cần lấy dữ liệu.
     * @return một bản ghi người dùng được điền đầy đủ (không bao giờ <code>null</code>)
     * @throws UsernameNotFoundException nếu không tìm thấy người dùng hoặc người dùng không có
     * GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userService.handleGetUserByEmail(email);
        if (user == null) {
            throw new AccountNotFoundException("Account not found!");
        }

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(), 
            user.getPassword(), 
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        
      
    }
}
