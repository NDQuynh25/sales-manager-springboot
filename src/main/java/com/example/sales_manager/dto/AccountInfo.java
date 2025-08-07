
package com.example.sales_manager.dto;

class AccountInfo {
    private String email;
    private String fullName;
    private String avatar;
    private Long cartId;
    private RoleDto role;

    public AccountInfo() {
    }

    public AccountInfo(String email, String fullName, String avatar, Long cartId, RoleDto role) {
        this.email = email;
        this.fullName = fullName;
        this.avatar = avatar;
        this.cartId = cartId;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }

    
}