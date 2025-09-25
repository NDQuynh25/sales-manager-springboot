package com.example.sales_manager.entity;


import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "carts", uniqueConstraints = {
    @UniqueConstraint(columnNames = "id")
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    // Quan hệ 1-1 với bảng User
    @OneToOne(mappedBy = "cart", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonManagedReference
    private User user;


    // quan hệ 1-n với CartItem
    @JsonManagedReference
    @OneToMany(
        mappedBy = "cart", 
        cascade = CascadeType.ALL, 
        fetch = FetchType.LAZY, 
        orphanRemoval = true // Xóa CartItem nếu bị remove khỏi list
    )
    private List<CartItem> cartItems;

}
