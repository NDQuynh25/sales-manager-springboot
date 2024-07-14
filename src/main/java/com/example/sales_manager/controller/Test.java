package com.example.sales_manager.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping()
public class Test {
    @GetMapping()
   
    public ResponseEntity<String> getMethodName() {
        return ResponseEntity.ok("Hello World");
    }
    
}
