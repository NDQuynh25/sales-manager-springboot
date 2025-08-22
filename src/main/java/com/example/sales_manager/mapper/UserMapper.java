package com.example.sales_manager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.cloudinary.provisioning.Account;
import com.example.sales_manager.dto.response.AccountInfoRes;
import com.example.sales_manager.dto.response.UserRes;
import com.example.sales_manager.entity.User;



@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(target = "cartId", source = "user", qualifiedByName = "mapToUserResWithCartId")
    UserRes mapToUserRes(User user);

    @Named("mapToUserResWithCartId")
    default Long mapToUserResWithCartId(User user) {
        
        return user.getCart().getId();
    }
    @Mapping(target = "cartId", source = "user.cart.id")
    AccountInfoRes mapToAccountInfoRes(User user);
}
