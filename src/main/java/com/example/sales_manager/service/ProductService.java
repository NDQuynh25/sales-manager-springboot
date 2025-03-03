package com.example.sales_manager.service;

import com.example.sales_manager.entity.Category;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.example.sales_manager.dto.request.ReqProductDto;
import com.example.sales_manager.dto.request.ReqProductDto.Sku;
import com.example.sales_manager.entity.SKU;
import com.example.sales_manager.dto.response.ResProductDto;
import com.example.sales_manager.entity.Product;
import com.example.sales_manager.entity.ProductImage;
import com.example.sales_manager.repository.ProductRepository;
import com.example.sales_manager.repository.ProductImageReponsitory;
import java.util.ArrayList;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductImageReponsitory productImageReponsitory;

   // private final CategoryService categoryService;

    private final FileService fileService;



    public ProductService(
            ProductRepository productRepository, 
            ProductImageReponsitory productImageReponsitory, 
           // @Lazy CategoryService categoryService, 
            FileService fileService) {
        this.productRepository = productRepository;
        this.productImageReponsitory = productImageReponsitory;
       // this.categoryService = categoryService;
        this.fileService = fileService;
    }

   // @Transactional
    public Product hanldeCreateProduct(ReqProductDto productDTO) throws Exception {
        try {
            Product product = new Product();
            product.setPromotionImageURL(productDTO.getPromotionImageURL());
            product.setProductName(productDTO.getProductName());
           // product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()));
            product.setDescription(productDTO.getDescription());
            product.setBrand(productDTO.getBrand());
            product.setCountryOfOrigin(productDTO.getCountryOfOrigin());
            product.setMaterials(productDTO.getMaterials());
            product.setPrice(productDTO.getPrice());
            product.setDiscount(productDTO.getDiscount());
            product.setQuantitySold(0L);
            product.setStock(productDTO.getStock());
            product.setVariation1(productDTO.getVariation1());
            product.setOptions1(productDTO.getOptions1());
            product.setVariation2(productDTO.getVariation2());
            product.setOptions2(productDTO.getOptions2());
            
            List<ProductImage> productImages = new ArrayList<>();
            for (String imageURL : productDTO.getImageURLs()) {
                ProductImage productImage = new ProductImage();
                productImage.setImageURL(imageURL);
                productImage.setProduct(product);
                productImages.add(productImage);
            }
            product.setProductImages(productImages);

            List<SKU> skus = new ArrayList<>();
//            for (Sku skuDTO : productDTO.getSkus()) {
//                SKU sku = new SKU();
//                sku.setImageURL(skuDTO.getImageURL());
//                sku.setOption1(skuDTO.getOption1());
//                sku.setOption2(skuDTO.getOption2());
//                sku.setPrice(skuDTO.getPrice());
//                sku.setStock(skuDTO.getStock());
//                sku.setDiscount(skuDTO.getDiscount());
//                sku.setProduct(product);
//                skus.add(sku);
//            }
            product.setSkus(skus);
        
            return productRepository.save(product);
        } catch (Exception e) {

            throw new Exception("Error creating product");
        }
    }

    public Long handleCountProductsByCategoryId(Long categoryId) throws Exception {
        try {
            return productRepository.countProductsByCategoryId(categoryId);
        } catch (Exception e) {
            throw new Exception("Error counting products by category id");
            
        }
    }
        

    public ResProductDto mapProductToResProductDto(Product product) {
        return new ResProductDto(
            product.getId()
           
        );
    }



}
