package com.example.sales_manager.dto.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkuReq {

    // private MultipartFile imageFile;
    private Long id;

    private String option1;

    private String option2;

    private BigDecimal originalPrice;

    private BigDecimal sellingPrice;

    private Long stock;

    private BigDecimal discount;

    private Integer isActive;

}
