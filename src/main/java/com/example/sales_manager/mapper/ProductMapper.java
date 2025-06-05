package com.example.sales_manager.mapper;

import com.example.sales_manager.dto.response.ProductBasicRes;
import com.example.sales_manager.dto.response.ProductDetailRes;
import com.example.sales_manager.dto.response.SkuRes;
import com.example.sales_manager.entity.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.Comparator;
import java.util.stream.Collectors;

import com.example.sales_manager.entity.Category;

@Mapper(componentModel = "spring", uses = {SkuMapper.class})
public interface ProductMapper {

   
    @Mapping(target = "categoryIds", ignore = true)
    ProductDetailRes mapToProductDetailResPartial(Product product);

    // Ánh xạ thủ công categoryIds trong phương thức chính
    default ProductDetailRes mapToProductDetailRes(Product product) {
        ProductDetailRes productDetailRes = mapToProductDetailResPartial(product);
        
        ProductDetailRes sortProductDetailRes = mapToProductDetailResPartial(product);
       
        // Sắp xếp skus theo sellingPrice
        sortProductDetailRes.getSkus().sort(
            Comparator.comparing(
                SkuRes::getSellingPrice,
                Comparator.nullsLast(Comparator.naturalOrder())
            )
            .thenComparing(
                SkuRes::getDiscount,
                Comparator.nullsLast(Comparator.reverseOrder()) 
            )
        );

        // Thiết lập giá hiển thị cho sellingPrice  
        productDetailRes.setSellingPriceDisplay(sortProductDetailRes.getSkus().stream()
            .map(SkuRes::getSellingPrice)
            .filter(sellingPrice -> sellingPrice != null)
            .findFirst()
            .orElse(null));

        // Thiết lập giá hiển thị cho discount
        productDetailRes.setDiscountDisplay(sortProductDetailRes.getSkus().stream()
            .map(SkuRes::getDiscount)
            .filter(discount -> discount != null)
            .findFirst()
            .orElse(null));

        // Thiết lập giá hiển thị cho originalPrice
        productDetailRes.setOriginalPriceDisplay(sortProductDetailRes.getSkus().stream()
            .map(SkuRes::getOriginalPrice)
            .filter(originalPrice -> originalPrice != null)
            .findFirst()
            .orElse(null));

        // Thiết lập giá hiển thị cho quantity
        long totalQuantitySold = product.getSkus().stream()
            .mapToLong(sku -> sku.getQuantitySold() != null ? sku.getQuantitySold() : 0)
            .sum();

        productDetailRes.setTotalQuantitySold(totalQuantitySold);

        // Thiết lập giá hiển thị cho stock

        long totalStock = product.getSkus().stream()
            .mapToLong(sku -> sku.getStock() != null ? sku.getStock() : 0)
            .sum();

        productDetailRes.setTotalStock(totalStock);


         // Ánh xạ thủ công categoryIds từ danh sách Category
        productDetailRes.setCategoryIds(product.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toList())); 
        
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
