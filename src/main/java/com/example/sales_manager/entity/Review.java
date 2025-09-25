package com.example.sales_manager.entity;

import java.time.Instant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "reviews", uniqueConstraints = {
    @UniqueConstraint(columnNames = "id")
})
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)    
    private Product product;

    @OneToOne(mappedBy = "review" ,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Order order;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "review_time")
    private Instant reviewTime;

    


    


    public Review() {
    }

    public Review(Product product, Order order, String content, Integer rating, Instant reviewTime) {
        this.product = product;
        this.order = order;
        this.content = content;
        this.rating = rating;
        this.reviewTime = reviewTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Instant getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Instant reviewTime) {
        this.reviewTime = reviewTime;
    }
}
