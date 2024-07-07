package com.example.sales_manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import java.util.List;

import org.springframework.security.core.AuthenticationException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(IdInvaildException.class)
    public ResponseEntity<RestResponse<Object>> handleIdInvaildException(IdInvaildException e) {
        RestResponse<Object> response = new RestResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Id Invaild Exception",
                e.getMessage(),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<Object>> handleGeneralException(Exception e) {
        RestResponse<Object> response = new RestResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                e.getMessage(),
                null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<RestResponse<Object>> handleDataNotFoundException(DataNotFoundException e) {
        RestResponse<Object> response = new RestResponse<>(
                HttpStatus.NOT_FOUND.value(),
                "Data Not Found",
                e.getMessage(),
                null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<RestResponse<Object>> handleBindException(BindException e) {
        List<String> errorMessages = e.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage()).toList();
                

        RestResponse<Object> response = new RestResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                errorMessages,
                null);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<RestResponse<Object>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        RestResponse<Object> response = new RestResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                e.getMessage(),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = {
        UsernameNotFoundException.class,
        BadCredentialsException.class
    })
    public ResponseEntity<RestResponse<Object>> handleAuthInforException(Exception e) {
        RestResponse<Object> response = new RestResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                e.getMessage(),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        RestResponse<Object> response = new RestResponse<>(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                ex.getMessage(),
                null);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
