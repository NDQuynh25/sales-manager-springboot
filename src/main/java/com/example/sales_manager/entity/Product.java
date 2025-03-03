package com.example.sales_manager.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.sales_manager.util.JsonConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "products", uniqueConstraints = {
    @UniqueConstraint(columnNames = "id")
})
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "promotion_image_URL", nullable = false)
    private String promotionImageURL;
    
    @Column(name = "product_name", nullable = false, length = 255)
    private String productName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "countryOfOrigin", nullable = false)
    private String countryOfOrigin;

    @Convert(converter = JsonConverter.class)
    @Column(name = "materials", nullable = false)
    private List<String> materials;

    @Column(name = "price")
    private Float price;

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


    // quan hệ n-n với bảng Category
    @ManyToMany(mappedBy = "products")
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SKU> skus;

    // Quan hệ 1-n với bảng Review
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    // Quan hệ 1-n với bảng ProductImage
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> productImages;

    // Quan hệ 1-n với bảng CartItem
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems;
    
    public Product() {
    }


    public Product(
            String promotionImageURL,
            String productName,
            String description,
            String brand,
            String countryOfOrigin,
            List<String> materials,
            Float price,
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
            List<ProductImage> productImages,
            List<CartItem> cartItems
    ) {
        this.promotionImageURL = promotionImageURL;
        this.productName = productName;
        this.price = price;
        this.discount = discount;
        this.quantitySold = quantitySold;
        this.stock = stock;
        this.description = description;
        this.variation1 = variation1;
        this.options1 = options1;
        this.variation2 = variation2;
        this.options2 = options2;
        this.skus = skus;
        this.categories = categories;
        this.reviews = reviews;
        this.productImages = productImages;
        this.cartItems = cartItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPromotionImageURL() {
        return promotionImageURL;
    }

    public void setPromotionImageURL(String promotionImageURL) {
        this.promotionImageURL = promotionImageURL;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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

    public List<Category> getCategory() {
        return categories;
    }

    public void setCategory(List<Category> categories) {
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

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
