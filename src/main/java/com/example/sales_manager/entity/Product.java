package com.example.sales_manager.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.sales_manager.util.converter.JsonConverter;

import jakarta.persistence.*;

@Entity
@Table(name = "products", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
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
    @Column(name = "description_image_URLs", nullable = false, columnDefinition = "TEXT")
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
    @ManyToMany(mappedBy = "products")
    private List<Category> categories = new ArrayList<>();

    // Quan hệ 1-n với bảng SKU
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SKU> skus;

    // Quan hệ 1-n với bảng Review
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    // Quan hệ 1-n với bảng CartItem
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems;

    public Product() {
    }

    public Product(
            String skuCode,
            List<String> productImageURLs,
            List<String> promotionImageURLs,
            List<String> descriptionImageURLs,
            String productName,
            String description,
            String brand,
            String countryOfOrigin,
            List<String> materials,
            Float originalPrice,
            Float sellingPrice,
            Float discount,
            Long quantitySold,
            Long stock,
            String variation1,
            List<String> options1,
            String variation2,
            List<String> options2,
            List<Category> categories,
            List<SKU> skus,
            List<Review> reviews,
            List<CartItem> cartItems) {
        this.skuCode = skuCode;
        this.productImageURLs = productImageURLs;
        this.promotionImageURLs = promotionImageURLs;
        this.descriptionImageURLs = descriptionImageURLs;
        this.productName = productName;
        this.description = description;
        this.brand = brand;
        this.countryOfOrigin = countryOfOrigin;
        this.materials = materials;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
        this.quantitySold = quantitySold;
        this.stock = stock;
        this.variation1 = variation1;
        this.options1 = options1;
        this.variation2 = variation2;
        this.options2 = options2;
        this.categories = categories;
        this.skus = skus;
        this.reviews = reviews;
        this.cartItems = cartItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public List<String> getProductImageURLs() {
        return productImageURLs;
    }

    public void setProductImageURLs(List<String> productImageURLs) {
        this.productImageURLs = productImageURLs;
    }

    public List<String> getPromotionImageURLs() {
        return promotionImageURLs;
    }

    public void setPromotionImageURLs(List<String> promotionImageURLs) {
        this.promotionImageURLs = promotionImageURLs;
    }

    public List<String> getDescriptionImageURLs() {
        return descriptionImageURLs;
    }

    public void setDescriptionImageURLs(List<String> descriptionImageURLs) {
        this.descriptionImageURLs = descriptionImageURLs;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public List<String> getMaterials() {
        return materials;
    }

    public void setMaterials(List<String> materials) {
        this.materials = materials;
    }

    public Float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Long getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Long quantitySold) {
        this.quantitySold = quantitySold;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getVariation1() {
        return variation1;
    }

    public void setVariation1(String variation1) {
        this.variation1 = variation1;
    }

    public List<String> getOptions1() {
        return options1;
    }

    public void setOptions1(List<String> options1) {
        this.options1 = options1;
    }

    public String getVariation2() {
        return variation2;
    }

    public void setVariation2(String variation2) {
        this.variation2 = variation2;
    }

    public List<String> getOptions2() {
        return options2;
    }

    public void setOptions2(List<String> options2) {
        this.options2 = options2;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<SKU> getSkus() {
        return skus;
    }

    public void setSkus(List<SKU> skus) {
        this.skus = skus;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

}
