package com.example.sales_manager.dto.response;




import com.fasterxml.jackson.annotation.JsonProperty;

public class ResLoginDto {

    public class User {
        @JsonProperty("id")
        private Long id;

        @JsonProperty("fullname")
        private String fullName;

        @JsonProperty("email")
        private String email;

        @JsonProperty("role")
        private ResRoleDto role;


        public User() {
        }

        public User(Long id, String fullName, String email, ResRoleDto role) {
            this.id = id;
            this.fullName = fullName;
            this.email = email;
            this.role = role;
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

        public void setRole(ResRoleDto role) {
            this.role = role;
        }
        public ResRoleDto getRole() {
            return role;
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
