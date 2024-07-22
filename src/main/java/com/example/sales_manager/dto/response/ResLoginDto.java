package com.example.sales_manager.dto.response;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResLoginDto {

    public class User {
        @JsonProperty("id")
        private Long id;

        @JsonProperty("full_name")
        private String fullName;

        @JsonProperty("email")
        private String email;

        @JsonProperty("role")
        private String role;

        @JsonProperty("permissions")
        private Set<String> permissions = new HashSet<>();

        public User() {
        }

        public User(Long id, String fullName, String email, String role, Set<String> permissions) {
            this.id = id;
            this.fullName = fullName;
            this.email = email;
            this.role = role;
            this.permissions = permissions;
        }

        public void setId(Long id) {
            this.id = id;
        }
        public Long getId() {
            return id;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
        public String getFullName() {
            return fullName;
        }

        public void setEmail(String email) {
            this.email = email;
        }
        public String getEmail() {
            return email;
        }

        public void setRole(String role) {
            this.role = role;
        }
        public String getRole() {
            return role;
        }

        public void setPermissions(Set<String> permissions) {
            this.permissions = permissions;
        }

        public Set<String> getPermissions() {
            return permissions;
        }

    }
    
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("user")
    private User user;

    
    

    public ResLoginDto() {
    }

    public ResLoginDto(String accessToken, User user) {
        this.accessToken = accessToken;
        this.user = user;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getAccessToken() {
        return accessToken;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
    
}
