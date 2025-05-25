package com.example.sales_manager.mapper;

import com.example.sales_manager.dto.response.ProductBasicRes;
import com.example.sales_manager.dto.response.ProductDetailRes;
import com.example.sales_manager.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import com.example.sales_manager.entity.Category;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // Bỏ qua categoryIds ở đây
    @Mapping(target = "categoryIds", ignore = true)
    ProductDetailRes mapToProductDetailResPartial(Product product);

    // Ánh xạ thủ công categoryIds trong phương thức chính
    default ProductDetailRes mapToProductDetailRes(Product product) {
        ProductDetailRes productDetailRes = mapToProductDetailResPartial(product); // Ánh xạ các trường khác
        productDetailRes.setCategoryIds(product.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toList())); // Ánh xạ thủ công categoryIds
        return productDetailRes;
    }

    ProductBasicRes mapToProductBasicRes(Product product);

    default Page<ProductBasicRes> mapToProductBasicResPage(Page<Product> productPage) {
        return productPage.map(this::mapToProductBasicRes);
    }

    default Page<ProductDetailRes> mapToProductDetailResPage(Page<Product> productPage) {
        return productPage.map(this::mapToProductDetailRes); // Sử dụng phương thức ánh xạ thủ công
    }
}
