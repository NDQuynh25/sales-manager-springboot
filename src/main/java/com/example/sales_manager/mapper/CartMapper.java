package com.example.sales_manager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.sales_manager.dto.response.CartRes;
import com.example.sales_manager.entity.Cart;



@Mapper(componentModel = "spring", uses = { CartItemMapper.class })
public interface CartMapper {
    
    @Mapping(target = "id", source = "cart.id")
    CartRes mapToCartRes(Cart cart);
    
    
    
}
