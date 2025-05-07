package com.example.sales_manager.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.sales_manager.util.converter.JsonConverter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "products", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sku_code", nullable = false)
    private String skuCode;

    @Convert(converter = JsonConverter.class)
    @Column(name = "product_image_URLs", nullable = false, columnDefinition = "TEXT")
    private List<String> productImageURLs;

    @Convert(converter = JsonConverter.class)
    @Column(name = "promotion_image_URLs", nullable = false, columnDefinition = "TEXT")
    private List<String> promotionImageURLs;

    @Convert(converter = JsonConverter.class)
    @Column(name = "description_images_URLs", nullable = false, columnDefinition = "TEXT")
    private List<String> descriptionImageURLs;

    @Column(name = "product_name", nullable = false, length = 255)
    private String productName;

    @Column(name = "description", nullable = false, columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "countryOfOrigin", nullable = false)
    private String countryOfOrigin;

    @Convert(converter = JsonConverter.class)
    @Column(name = "materials", nullable = false)
    private List<String> materials;

    @Column(name = "original_price", nullable = false)
    private Float originalPrice;

    @Column(name = "selling_price", nullable = false)
    private Float sellingPrice;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "quantity_sold", nullable = false)
    private Long quantitySold; // Number of products sold

    @Column(name = "stock")
    private Long stock; // Remaining quantity

    @Column(name = "variation_1")
    private String variation1;

    @Column(name = "options_1")
    @Convert(converter = JsonConverter.class)
    private List<String> options1;

    @Column(name = "variation_2")
    private String variation2;

    @Column(name = "options_2")
    @Convert(converter = JsonConverter.class)
    private List<String> options2;

    // Quan hệ n-n với bảng Category
    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("products")
    @Builder.Default
    private List<Category> categories = new ArrayList<>();

    // Quan hệ 1-n với bảng SKU
    @OneToMany(mappedBy = "product", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @Builder.Default
    private List<SKU> skus = new ArrayList<>();

    // Quan hệ 1-n với bảng Review
    @OneToMany(mappedBy = "product", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    // Quan hệ 1-n với bảng CartItem
    @OneToMany(mappedBy = "product", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CartItem> cartItems = new ArrayList<>();

}