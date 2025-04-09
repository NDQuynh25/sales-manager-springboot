package com.example.sales_manager.service;



import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.sales_manager.dto.ResultPagination;
import com.example.sales_manager.dto.response.ProductDetailRes;
import com.example.sales_manager.dto.response.ProductRes;
import com.example.sales_manager.dto.response.SummaryProductRes;
import com.example.sales_manager.entity.Product;
import com.example.sales_manager.exception.DataNotFoundException;
import com.example.sales_manager.repository.ProductRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class ProductService {

    private final ProductRepository productRepository;



    // private final CategoryService categoryService;

    private final FileService fileService;

    private final SkuService skuService;

    public ProductService(
            ProductRepository productRepository,
            // @Lazy CategoryService categoryService,
            FileService fileService,
            SkuService skuService) {
        this.productRepository = productRepository;
        // this.categoryService = categoryService;
        this.fileService = fileService;
        this.skuService = skuService;
    }

    // // @Transactional
    // public Product hanldeCreateProduct(ProductReq productDTO) throws Exception {
    //     try {
    //         Product product = new Product();
    //         product.setPromotionImageURL(productDTO.getPromotionImageURL());
    //         product.setProductName(productDTO.getProductName());
    //         // product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()));
    //         product.setDescription(productDTO.getDescription());
    //         product.setBrand(productDTO.getBrand());
    //         product.setCountryOfOrigin(productDTO.getCountryOfOrigin());
    //         product.setMaterials(productDTO.getMaterials());
    //         product.setPrice(productDTO.getPrice());
    //         product.setDiscount(productDTO.getDiscount());
    //         product.setQuantitySold(0L);
    //         product.setStock(productDTO.getStock());
    //         product.setVariation1(productDTO.getVariation1());
    //         product.setOptions1(productDTO.getOptions1());
    //         product.setVariation2(productDTO.getVariation2());
    //         product.setOptions2(productDTO.getOptions2());

    //         List<ProductImage> productImages = new ArrayList<>();
    //         for (String imageURL : productDTO.getImageURLs()) {
    //             ProductImage productImage = new ProductImage();
    //             productImage.setImageURL(imageURL);
    //             productImage.setProduct(product);
    //             productImages.add(productImage);
    //         }
    //         product.setProductImages(productImages);

    //         List<SKU> skus = new ArrayList<>();
            // for (Sku skuDTO : productDTO.getSkus()) {
            // SKU sku = new SKU();
            // sku.setImageURL(skuDTO.getImageURL());
            // sku.setOption1(skuDTO.getOption1());
            // sku.setOption2(skuDTO.getOption2());
            // sku.setPrice(skuDTO.getPrice());
            // sku.setStock(skuDTO.getStock());
            // sku.setDiscount(skuDTO.getDiscount());
            // sku.setProduct(product);
            // skus.add(sku);
            // }
    //         product.setSkus(skus);

    //         return productRepository.save(product);
    //     } catch (Exception e) {

    //         throw new Exception("Error creating product");
    //     }
    // }

    public Product handleGetProductById(Long id) throws Exception {
        try {
            return productRepository.findProductById(id);
        } catch (Exception e) {
            throw new Exception("Error getting product by id");
        }
    }

    public ResultPagination handleGetProducts(Specification<Product> spec, Pageable pageable, Boolean isSummary) throws Exception {

        try {
            ResultPagination resultPagination = new ResultPagination();
            ResultPagination.Meta meta = resultPagination.new Meta();

            Page<Product> products = productRepository.findAll(spec, pageable);
            if (products.isEmpty()) {
                throw new DataNotFoundException("No products found");
            }

            List<ProductRes> productRes;
            List<SummaryProductRes> summaryProductRes;

            if (isSummary) {
                summaryProductRes = products.getContent().stream().map(item -> this.mapProductToSummaryProductRes(item)).toList();
                resultPagination.setResult(summaryProductRes);

            } else {
                productRes = products.getContent().stream().map(item -> this.mapProductToProductRes(item)).toList();
                resultPagination.setResult(productRes);
            }
            
            meta.setPage(products.getNumber());
            meta.setPageSize(products.getSize());
            meta.setTotalPages(products.getTotalPages());
            meta.setTotalElements(products.getTotalElements());
            resultPagination.setMeta(meta);

            return resultPagination;
        } catch (Exception e) {
            throw new Exception("Error getting products");
        }    
    }

    public Long handleCountProductsByCategoryId(Long categoryId) throws Exception {
        try {
            return productRepository.countProductsByCategoryId(categoryId);
        } catch (Exception e) {
            throw new Exception("Error counting products by category id");

        }
    }

    public ProductRes mapProductToProductRes(Product product) {
        return new ProductRes(
                product.getId()

        );
    }

    public SummaryProductRes mapProductToSummaryProductRes(Product product) {
        return new SummaryProductRes(
                product.getId(),
                product.getImageURLs(),
                product.getPromotionImageURLs(),
                product.getProductName(),
                product.getSellingPrice(),
                product.getDiscount(),
                product.getQuantitySold(),
                product.getIsActive(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );  
                
    }

    public ProductDetailRes mapProductToProductDetailRes(Product product) {
        ProductDetailRes productRes = new ProductDetailRes();

        productRes.setId(product.getId());
        productRes.setSkuCode(product.getSkuCode());
        productRes.setImageURLs(product.getImageURLs());
        productRes.setPromotionImageURLs(product.getPromotionImageURLs());
        productRes.setProductName(product.getProductName());
        productRes.setDescription(product.getDescription());
        productRes.setBrand(product.getBrand());
        productRes.setCountryOfOrigin(product.getCountryOfOrigin());
        productRes.setMaterials(product.getMaterials());
        productRes.setOriginalPrice(product.getOriginalPrice());
        productRes.setSellingPrice(product.getSellingPrice());
        productRes.setDiscount(product.getDiscount());
        productRes.setStock(product.getStock());
        productRes.setQuantitySold(product.getQuantitySold());
        productRes.setVariation1(product.getVariation1());
        productRes.setOptions1(product.getOptions1());
        productRes.setVariation2(product.getVariation2());
        productRes.setOptions2(product.getOptions2());
        productRes.setCategoryIds(product.getCategories().stream().map(category -> category.getId()).toList());
        productRes.setSkus(product.getSkus().stream().map(sku -> skuService.mapSKUToSkuRes(sku)).toList());
        productRes.setIsActive(product.getIsActive());
        productRes.setCreatedAt(product.getCreatedAt());
        productRes.setUpdatedAt(product.getUpdatedAt());
        productRes.setCreatedBy(product.getCreatedBy());
        productRes.setUpdatedBy(product.getUpdatedBy());
        
        
        return productRes;
    }



}
