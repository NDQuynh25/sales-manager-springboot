package com.example.sales_manager.dto.request;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class OrderItemReq {

    @NotNull(message = "SKU ID không được để trống")
    private Long skuId;

    @NotNull(message = "Product ID không được để trống")
    private Long productId;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải ít nhất là 1")
    private Integer quantity;

    @NotNull(message = "Giá gốc không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá gốc phải > 0")
    private BigDecimal originalPriceAtOrderTime;

    @NotNull(message = "Giảm giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Giảm giá không được âm")
    private BigDecimal discountAtOrderTime;

}