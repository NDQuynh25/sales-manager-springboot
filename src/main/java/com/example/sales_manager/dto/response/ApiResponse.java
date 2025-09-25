package com.example.sales_manager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {

    private int status;
    private String error; // "Bad Request", "Not Found", "Internal Server Error", "Unauthorized",
                          // "Forbidden", "Conflict", "Precondition Failed", "Unsupported Media Type",
                          // "Unprocessable Entity", "Too Many Requests", "Service Unavailable", "Gateway
                          // Timeout", "Not Implemented", "Bad Gateway", "Gateway Timeout", "HTTP Version
                          // Not Supported", "Variant Also Negotiates", "Insufficient Storage", "Loop
                          // Detected", "Not Extended", "Network Authentication Required

    // Message can be a String or a List<String>
    private Object message;
    private T data;

}