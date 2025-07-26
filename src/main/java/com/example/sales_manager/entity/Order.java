package com.example.sales_manager.entity;

import java.time.Instant;
import java.util.List;

import com.example.sales_manager.util.constant.OrderStatusEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Quan hệ n-1 với bảng User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "recipientName")
    private String recipientName;

    @Column(name = "recipientPhone")
    private String recipientPhone;
    
    @Column(name = "recipientCodeProvince")
    private String recipientCodeProvince;

    @Column(name = "recipientCodeDistrict")
    private String recipientCodeDistrict;

    @Column(name = "recipientCodeWard")
    private String recipientCodeWard;

    @Column(name = "recipientAddress")
    private String recipientAddress;

    // Quan hệ 1-n với bảng Bill (có thể thanh toán nhiều lần)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bill> bills;

    // Quan hệ 1-n với bảng OrderItem 
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    // Quan hệ 1-1 với bảng Review
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(name = "order_date")
    private Instant orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatusEnum status;

    @Column(name = "note", length = 500)
    private String note;
}
