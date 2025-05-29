package com.example.sales_manager.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuRes {
    private Long id;

    private String option1;

    private String option2;

    private BigDecimal originalPrice;

    private BigDecimal sellingPrice;

    private BigDecimal discount;

    private Long stock;

    private Long quantitySold;

    private Integer isActive;

}
