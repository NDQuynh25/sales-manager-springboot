package com.example.sales_manager.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor  
@AllArgsConstructor
public class CartRes {
    
    private Long id;

    private List<CartItemRes> cartItems;

    // private List<CartItemRes> cartItems;
}
