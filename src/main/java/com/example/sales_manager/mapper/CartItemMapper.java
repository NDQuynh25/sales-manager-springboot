package com.example.sales_manager.mapper;

import org.mapstruct.Mapper;
import com.example.sales_manager.dto.response.CartItemRes;
import com.example.sales_manager.entity.CartItem;


@Mapper(componentModel = "spring", uses = { SkuMapper.class, ProductMapper.class})
public interface CartItemMapper {
    
    
    CartItemRes mapToCartItemRes(CartItem cartItem);


}
