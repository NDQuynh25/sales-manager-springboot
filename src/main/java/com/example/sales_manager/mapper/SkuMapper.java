package com.example.sales_manager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.sales_manager.dto.response.SkuRes;
import com.example.sales_manager.entity.SKU;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper(componentModel = "spring")
public interface SkuMapper {

    @Mapping(target = "sellingPrice", source = "sku", qualifiedByName = "calculateSellingPrice")
    SkuRes mapToSkuRes(SKU sku);

    @Named("calculateSellingPrice")
    default BigDecimal calculateSellingPrice(SKU sku) {
        if (sku.getOriginalPrice() == null || sku.getDiscount() == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal hundred = BigDecimal.valueOf(100);
        BigDecimal discountMultiplier = hundred.subtract(sku.getDiscount())
                                            .divide(hundred);
        return sku.getOriginalPrice()
              .multiply(discountMultiplier)
              .setScale(0, RoundingMode.HALF_UP); 
    }

    

}
