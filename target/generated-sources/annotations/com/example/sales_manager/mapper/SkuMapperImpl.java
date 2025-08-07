package com.example.sales_manager.mapper;

import com.example.sales_manager.dto.response.SkuRes;
import com.example.sales_manager.entity.SKU;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-07T17:51:50+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class SkuMapperImpl implements SkuMapper {

    @Override
    public SkuRes mapToSkuRes(SKU sku) {
        if ( sku == null ) {
            return null;
        }

        SkuRes.SkuResBuilder skuRes = SkuRes.builder();

        skuRes.sellingPrice( calculateSellingPrice( sku ) );
        skuRes.discount( sku.getDiscount() );
        skuRes.id( sku.getId() );
        skuRes.isActive( sku.getIsActive() );
        skuRes.option1( sku.getOption1() );
        skuRes.option2( sku.getOption2() );
        skuRes.originalPrice( sku.getOriginalPrice() );
        skuRes.quantitySold( sku.getQuantitySold() );
        skuRes.stock( sku.getStock() );

        return skuRes.build();
    }
}
