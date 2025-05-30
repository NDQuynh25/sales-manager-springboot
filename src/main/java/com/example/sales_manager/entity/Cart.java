package com.example.sales_manager.entity;


import java.util.List;

import com.example.sales_manager.domain.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    // Quan hệ 1-1 với bảng User
    @OneToOne(mappedBy = "cart",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    // quan hệ 1-n với CartItem
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems;
    




    public Cart() {
    }

    public Cart(User user, List<CartItem> cartItems) {
        this.user = user;
        this.cartItems = cartItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

}
