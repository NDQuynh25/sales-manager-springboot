package com.example.sales_manager.mapper;

import com.example.sales_manager.dto.response.CartItemRes;
import com.example.sales_manager.dto.response.CartRes;
import com.example.sales_manager.entity.Cart;
import com.example.sales_manager.entity.CartItem;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-07T17:59:26+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Autowired
    private CartItemMapper cartItemMapper;

    @Override
    public CartRes mapToCartRes(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        CartRes.CartResBuilder cartRes = CartRes.builder();

        cartRes.id( cart.getId() );
        cartRes.cartItems( cartItemListToCartItemResList( cart.getCartItems() ) );

        cartRes.numberOfItems( cart.getCartItems() != null ? cart.getCartItems().size() : 0 );

        return cartRes.build();
    }

    protected List<CartItemRes> cartItemListToCartItemResList(List<CartItem> list) {
        if ( list == null ) {
            return null;
        }

        List<CartItemRes> list1 = new ArrayList<CartItemRes>( list.size() );
        for ( CartItem cartItem : list ) {
            list1.add( cartItemMapper.mapToCartItemRes( cartItem ) );
        }

        return list1;
    }
}
