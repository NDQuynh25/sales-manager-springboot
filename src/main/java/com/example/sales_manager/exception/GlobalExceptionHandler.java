package com.example.sales_manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public interface GlobalExceptionHandler {

    @ExceptionHandler(IdInvaildException.class)
    ResponseEntity<RestResponse<Object>> handleIdInvaildException(IdInvaildException e);

    @ExceptionHandler(Exception.class)
    ResponseEntity<RestResponse<Object>> handleGeneralException(Exception e);

    @ExceptionHandler(DataNotFoundException.class)
    ResponseEntity<RestResponse<Object>> handleDataNotFoundException(DataNotFoundException e);

    @ExceptionHandler(BindException.class)
    ResponseEntity<RestResponse<Object>> handleBindException(BindException e);

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<RestResponse<Object>> handleDataIntegrityViolationException(DataIntegrityViolationException e);

    @ExceptionHandler(AccountNotFoundException.class)
    ResponseEntity<RestResponse<Object>> handleAccountNotFoundException(AccountNotFoundException e);

    default ResponseEntity<RestResponse<Object>> handleDefaultException(Exception e, HttpStatus status, String defaultMessage) {
        RestResponse<Object> response = new RestResponse<>(
                status.value(),
                defaultMessage,
                e.getMessage(),
                null);

        return ResponseEntity.status(status).body(response);
    }

    default ResponseEntity<RestResponse<Object>> handleValidationExceptions(BindException ex) {
        List<String> errorMessages = ex.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage()).toList();

        RestResponse<Object> response = new RestResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                errorMessages,
                null);

        return ResponseEntity.badRequest().body(response);
    }
}

