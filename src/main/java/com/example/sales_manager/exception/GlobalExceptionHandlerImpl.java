package com.example.sales_manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import java.util.List;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlerImpl implements GlobalExceptionHandler {

    @Override
    public ResponseEntity<RestResponse<Object>> handleIdInvaildException(IdInvaildException e) {
        RestResponse<Object> response = new RestResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Id Invaild Exception",
                e.getMessage(),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @Override
    public ResponseEntity<RestResponse<Object>> handleGeneralException(Exception e) {
        RestResponse<Object> response = new RestResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                e.getMessage(),
                null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @Override
    public ResponseEntity<RestResponse<Object>> handleDataNotFoundException(DataNotFoundException e) {
        RestResponse<Object> response = new RestResponse<>(
                HttpStatus.NOT_FOUND.value(),
                "Data Not Found",
                e.getMessage(),
                null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @Override
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

    @Override
    public ResponseEntity<RestResponse<Object>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        RestResponse<Object> response = new RestResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                e.getMessage(),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @Override
    public ResponseEntity<RestResponse<Object>> handleAccountNotFoundException(AccountNotFoundException e) {
        RestResponse<Object> response = new RestResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Account Not Found",
                e.getMessage(),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
