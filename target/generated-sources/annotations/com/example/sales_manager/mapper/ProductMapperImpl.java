package com.example.sales_manager.mapper;

import com.example.sales_manager.dto.response.ProductBasicRes;
import com.example.sales_manager.dto.response.ProductDetailRes;
import com.example.sales_manager.dto.response.SkuRes;
import com.example.sales_manager.entity.Product;
import com.example.sales_manager.entity.SKU;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-22T10:32:06+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Autowired
    private SkuMapper skuMapper;

    @Override
    public ProductDetailRes mapToProductDetailResPartial(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDetailRes.ProductDetailResBuilder productDetailRes = ProductDetailRes.builder();

        productDetailRes.brand( product.getBrand() );
        productDetailRes.countryOfOrigin( product.getCountryOfOrigin() );
        productDetailRes.createdAt( product.getCreatedAt() );
        productDetailRes.createdBy( product.getCreatedBy() );
        productDetailRes.description( product.getDescription() );
        productDetailRes.id( product.getId() );
        productDetailRes.isActive( product.getIsActive() );
        List<String> list = product.getMaterials();
        if ( list != null ) {
            productDetailRes.materials( new ArrayList<String>( list ) );
        }
        List<String> list1 = product.getOptions1();
        if ( list1 != null ) {
            productDetailRes.options1( new ArrayList<String>( list1 ) );
        }
        List<String> list2 = product.getOptions2();
        if ( list2 != null ) {
            productDetailRes.options2( new ArrayList<String>( list2 ) );
        }
        List<String> list3 = product.getProductImageURLs();
        if ( list3 != null ) {
            productDetailRes.productImageURLs( new ArrayList<String>( list3 ) );
        }
        productDetailRes.productName( product.getProductName() );
        List<String> list4 = product.getPromotionImageURLs();
        if ( list4 != null ) {
            productDetailRes.promotionImageURLs( new ArrayList<String>( list4 ) );
        }
        productDetailRes.skus( sKUListToSkuResList( product.getSkus() ) );
        productDetailRes.updatedAt( product.getUpdatedAt() );
        productDetailRes.updatedBy( product.getUpdatedBy() );
        productDetailRes.variation1( product.getVariation1() );
        productDetailRes.variation2( product.getVariation2() );

        return productDetailRes.build();
    }

    @Override
    public ProductBasicRes mapToProductBasicRes(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductBasicRes.ProductBasicResBuilder productBasicRes = ProductBasicRes.builder();

        productBasicRes.id( product.getId() );
        productBasicRes.productName( product.getProductName() );
        List<String> list = product.getPromotionImageURLs();
        if ( list != null ) {
            productBasicRes.promotionImageURLs( new ArrayList<String>( list ) );
        }

        return productBasicRes.build();
    }

    protected List<SkuRes> sKUListToSkuResList(List<SKU> list) {
        if ( list == null ) {
            return null;
        }

        List<SkuRes> list1 = new ArrayList<SkuRes>( list.size() );
        for ( SKU sKU : list ) {
            list1.add( skuMapper.mapToSkuRes( sKU ) );
        }

        return list1;
    }
}
