package com.example.sales_manager.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductBasicRes {

    private Long id;

    private List<String> promotionImageURLs;

    private String productName;

    private Float sellingPrice;

    private Float discount;

}
