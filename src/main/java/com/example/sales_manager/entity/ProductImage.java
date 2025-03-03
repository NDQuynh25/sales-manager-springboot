package com.example.sales_manager.entity;

import com.example.sales_manager.util.constant.ImageEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "product_images", uniqueConstraints = {
    @UniqueConstraint(columnNames = "id")
})
public class ProductImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;  // Liên kết với bảng Product

    @Column(name = "image_url", nullable = false)
    private String imageURL;  // Đường dẫn URL của ảnh

    @Column(name = "image_type", nullable = false)
    @Enumerated(EnumType.STRING)
   
    private ImageEnum imageType ;  //

    public ProductImage() {
    }

    public ProductImage(Product product, String imageURL, ImageEnum imageType) {
        this.product = product;
        this.imageURL = imageURL;
        this.imageType = imageType;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ImageEnum getImageType() {
        return imageType;
    }

    public void setImageType(ImageEnum imageType) {
        this.imageType = imageType;
    }
}

