package com.example.sales_manager.mapper;

import com.example.sales_manager.dto.response.CartItemRes;
import com.example.sales_manager.dto.response.SkuRes;
import com.example.sales_manager.entity.CartItem;
import com.example.sales_manager.entity.Product;
import com.example.sales_manager.entity.SKU;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-10T13:38:13+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class CartItemMapperImpl implements CartItemMapper {

    @Autowired
    private SkuMapper skuMapper;

    @Override
    public CartItemRes mapToCartItemRes(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }

        CartItemRes.CartItemResBuilder cartItemRes = CartItemRes.builder();

        cartItemRes.id( cartItem.getId() );
        cartItemRes.isActive( cartItem.getIsActive() );
        cartItemRes.product( productToProduct( cartItem.getProduct() ) );
        cartItemRes.quantity( cartItem.getQuantity() );
        cartItemRes.sku( skuMapper.mapToSkuRes( cartItem.getSku() ) );

        return cartItemRes.build();
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

    protected CartItemRes.Product productToProduct(Product product) {
        if ( product == null ) {
            return null;
        }

        CartItemRes.Product.ProductBuilder product1 = CartItemRes.Product.builder();

        product1.id( product.getId() );
        if ( product.getIsActive() != null ) {
            product1.isActive( product.getIsActive().longValue() );
        }
        List<String> list = product.getOptions1();
        if ( list != null ) {
            product1.options1( new ArrayList<String>( list ) );
        }
        List<String> list1 = product.getOptions2();
        if ( list1 != null ) {
            product1.options2( new ArrayList<String>( list1 ) );
        }
        product1.productName( product.getProductName() );
        List<String> list2 = product.getPromotionImageURLs();
        if ( list2 != null ) {
            product1.promotionImageURLs( new ArrayList<String>( list2 ) );
        }
        product1.skus( sKUListToSkuResList( product.getSkus() ) );
        product1.variation1( product.getVariation1() );
        product1.variation2( product.getVariation2() );

        return product1.build();
    }
}
