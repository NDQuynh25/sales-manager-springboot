package com.example.sales_manager.dto.response;



public class RestResponse<T> {

    private int status;
    private String error; // "Bad Request", "Not Found", "Internal Server Error", "Unauthorized", "Forbidden", "Conflict", "Precondition Failed", "Unsupported Media Type", "Unprocessable Entity", "Too Many Requests", "Service Unavailable", "Gateway Timeout", "Not Implemented", "Bad Gateway", "Gateway Timeout", "HTTP Version Not Supported", "Variant Also Negotiates", "Insufficient Storage", "Loop Detected", "Not Extended", "Network Authentication Required

    // Message can be a String or a List<String>
    private Object message;
    private T data;

    public RestResponse() {
    }

    public RestResponse(int status, String error, Object message, T data) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }

    public Object getMessage() {
        return message;
    }
    public void setMessage(Object message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}