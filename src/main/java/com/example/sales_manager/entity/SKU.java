package com.example.sales_manager.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "skus", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SKU extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "option1", nullable = false)
    private String option1;

    @Column(name = "option2")
    private String option2;

    @Column(name = "original_price", nullable = false)
    private BigDecimal originalPrice;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "stock", nullable = false)
    private Long stock;

    @Column(name = "quantity_sold", nullable = false)
    private Long quantitySold; // Number of products sold

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "sku", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "sku", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

}