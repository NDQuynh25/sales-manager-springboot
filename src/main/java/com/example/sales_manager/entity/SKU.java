package com.example.sales_manager.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "skus", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
public class SKU extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="sku_code", nullable = false)
    private String skuCode;

    @Column(name="option1", nullable = false)
    private String option1;

    @Column(name="option2")
    private String option2;

    @Column(name="original_price", nullable = false)
    private Float originalPrice;

    @Column(name="selling_price", nullable = false)
    private Float sellingPrice;

    @Column(name="discount")
    private Float discount;

    @Column(name="stock", nullable = false)
    private Integer stock;

    @Column(name = "quantity_sold", nullable = false)
    private Long quantitySold; // Number of products sold

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false, referencedColumnName = "id")
    private Product product;


    public SKU() {
    }

    public SKU(String skuCode, String option1, String option2, Float originalPrice, Float sellingPrice, Float discount, Integer stock, Long quantitySold, Product product) {
        this.skuCode = skuCode;
        this.option1 = option1;
        this.option2 = option2;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
        this.stock = stock;
        this.quantitySold = quantitySold;
        this.product = product;
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

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Long quantitySold) {
        this.quantitySold = quantitySold;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
