package com.example.sales_manager.dto.response;

import java.util.List;

public class ProductRes {

    private Long id;

    private List<String> imageURLs;

    private String productName;

    private String description;

    private String brand;

    private String countryOfOrigin;

    private List<String> materials;

    private Float originalPrice;

    private Float sellingPrice;

    private Float discount;

    



    

    public ProductRes(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
