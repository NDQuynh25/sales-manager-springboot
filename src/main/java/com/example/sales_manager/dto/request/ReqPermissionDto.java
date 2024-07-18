package com.example.sales_manager.dto.request;

public class ReqPermissionDto {
    private String name;

    private String apiAccess;

    private String method;

    private String description;

    public ReqPermissionDto() {
    }

    public ReqPermissionDto(String name, String apiAccess, String method, String description) {
        this.name = name;
        this.apiAccess = apiAccess;
        this.method = method;
        this.description = description;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiAccess() {
        return apiAccess;
    }

    public void setApiAccess(String apiAccess) {
        this.apiAccess = apiAccess;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    

    

}
