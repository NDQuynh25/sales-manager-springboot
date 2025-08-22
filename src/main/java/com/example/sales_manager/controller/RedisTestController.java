package com.example.sales_manager.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/redis")
public class RedisTestController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    
    @PostConstruct
    public void logRedisFactoryClass() {
        System.out.println("✅ RedisConnectionFactory class: " + redisConnectionFactory.getClass().getName());
    }
    @GetMapping("/ping")
    public String ping() {
        try {
            // Ghi giá trị vào Redis với TTL = 5 phút
            redisTemplate.opsForValue().set("ping", "pong", Duration.ofMinutes(5));

            // Đọc lại giá trị từ Redis
            String value = redisTemplate.opsForValue().get("ping");

            return "Redis hoạt động: " + value;
        } catch (Exception e) {
            return "Kết nối Redis thất bại: " + e.getMessage();
        }
    }
}
