package com.example.sales_manager.entity;

import java.util.List;

import com.example.sales_manager.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "categories", uniqueConstraints = {
    @UniqueConstraint(columnNames = "id")
})
public class Category extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", nullable = false, length = 255)
    private String categoryName;

    // quan hệ 1-n với bảng Product
    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @Column(name = "image_url", nullable = false)
    private String image_url; // URL of the image of the category


    // Constructors, Getters, Setters methods

    public Category() {
    }

    public Category(String categoryName, List<Product> products, String image_url) {
        this.categoryName = categoryName;
        this.products = products;
        this.image_url = image_url;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getImage_url() {
        return this.image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

}

