package com.example.sales_manager.service;

import java.util.ArrayList;
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
import com.example.sales_manager.entity.Category;
import com.example.sales_manager.entity.Product;
import com.example.sales_manager.entity.SKU;
import com.example.sales_manager.exception.DataNotFoundException;
import com.example.sales_manager.repository.ProductRepository;
import com.example.sales_manager.repository.SkuRepository;
import com.example.sales_manager.dto.request.ProductReq;
import com.example.sales_manager.dto.request.SkuReq;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {

    private final CategoryService categoryService;

    private final ProductRepository productRepository;

    // private final CategoryService categoryService;

    private final FileService fileService;

    private final SkuService skuService;

    private final SkuRepository skuRepository;

    public ProductService(
            ProductRepository productRepository,
            @Lazy CategoryService categoryService,
            FileService fileService,
            SkuService skuService,
            SkuRepository skuRepository) {
        this.productRepository = productRepository;
        // this.categoryService = categoryService;
        this.fileService = fileService;
        this.skuService = skuService;
        this.skuRepository = skuRepository;
        this.categoryService = categoryService;
    }

    // @Transactional
    @Transactional
    public Product handleCreateProduct(ProductReq productReq) throws Exception {
        List<String> productImageURLs = null;
        List<String> promotionImageURLs = null;
        List<String> descriptionImageURLs = new ArrayList<>();

        try {
            // Upload ảnh
            if (productReq.getProductImages() != null && !productReq.getProductImages().isEmpty()) {
                productImageURLs = fileService
                        .handleUploadMultipleFiles(productReq.getProductImages().toArray(new MultipartFile[0]));
            }

            if (productReq.getPromotionImages() != null && !productReq.getPromotionImages().isEmpty()) {
                promotionImageURLs = fileService
                        .handleUploadMultipleFiles(productReq.getPromotionImages().toArray(new MultipartFile[0]));
            }

            // Thay thế url vào description

            String newDescription = productReq.getDescription();
            System.out.println("[INFO] description: " + newDescription);
            if (productReq.getDescriptionImages() != null && !productReq.getDescriptionImages().isEmpty()) {
                for (MultipartFile file : productReq.getDescriptionImages()) {
                    String newImageUrl = fileService.handleUploadFile(file);
                    descriptionImageURLs.add(newImageUrl);
                    System.out.println("[INFO] newImageUrl: " + newImageUrl);
                    String oldImageUrl = "https://example.com/" + file.getOriginalFilename() + ".jpg";

                    newDescription = newDescription.replace(oldImageUrl, newImageUrl);
                }
                System.out.println("[INFO] newDescription: " + newDescription);
            }

            // Tạo sản phẩm
            Product product = createProductEntity(productReq, productImageURLs, promotionImageURLs,
                    descriptionImageURLs);

            // Lưu các danh mục
            // List<Category> categories = new ArrayList<>();
            // for (Long categoryId : productReq.getCategoryIds()) {
            // Category category = categoryService.handleGetCategoryById(categoryId);
            // if (category != null && category.getIsActive() == 1) {
            // categories.add(category);
            // }
            // }
            // product.setCategories(categories);

            // Lưu sản phẩm
            Product savedProduct = productRepository.save(product);
            // Lưu các SKU
            saveSkus(productReq.getSkus(), savedProduct);

            return savedProduct;
        } catch (Exception e) {
            // Xóa các file đã upload nếu có lỗi
            if (productImageURLs != null)
                fileService.handleDeleteMultipleFiles(productImageURLs.toArray(new String[0]));
            if (promotionImageURLs != null)
                fileService.handleDeleteMultipleFiles(promotionImageURLs.toArray(new String[0]));
            if (descriptionImageURLs != null)
                fileService.handleDeleteMultipleFiles(descriptionImageURLs.toArray(new String[0]));

            LoggerFactory.getLogger(ProductService.class).error("Error creating product", e);
            throw new Exception("Error creating product: " + e.getMessage(), e);
        }
    }

    private Product createProductEntity(ProductReq productReq, List<String> productImages,
            List<String> promotionImages, List<String> descriptionImages) throws Exception {

        Product product = new Product();
        // Set all properties
        product.setSkuCode(productReq.getSkuCode());
        product.setProductImageURLs(productImages);
        product.setPromotionImageURLs(promotionImages);
        product.setDescriptionImageURLs(descriptionImages);
        product.setProductName(productReq.getProductName());
        product.setDescription(productReq.getDescription());
        product.setBrand(productReq.getBrand());
        product.setCountryOfOrigin(productReq.getCountryOfOrigin());
        product.setMaterials(productReq.getMaterials());
        product.setOriginalPrice(productReq.getOriginalPrice());
        product.setSellingPrice(productReq.getSellingPrice());
        product.setDiscount(productReq.getDiscount());
        product.setStock(productReq.getStock());
        product.setVariation1(productReq.getVariation1());
        product.setOptions1(productReq.getOptions1());
        product.setVariation2(productReq.getVariation2());
        product.setOptions2(productReq.getOptions2());
        product.setQuantitySold(0L);

        return product;
    }

    private void saveSkus(List<SkuReq> skus, Product product) throws Exception {
        for (SkuReq skuReq : skus) {
            SKU sku = new SKU();
            sku.setSkuCode(skuReq.getSkuCode());
            sku.setOption1(skuReq.getOption1());
            sku.setOption2(skuReq.getOption2());
            sku.setOriginalPrice(skuReq.getOriginalPrice());
            sku.setSellingPrice(skuReq.getSellingPrice());
            sku.setStock(skuReq.getStock());
            sku.setDiscount(skuReq.getDiscount());

            sku.setProduct(product);
            skuRepository.save(sku);
        }
    }

    public Product handleGetProductById(Long id) throws Exception {
        try {
            return productRepository.findProductById(id);
        } catch (Exception e) {
            throw new Exception("Error getting product by id");
        }
    }

    public ResultPagination handleGetProducts(Specification<Product> spec, Pageable pageable, Boolean isSummary)
            throws Exception {

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
                summaryProductRes = products.getContent().stream().map(item -> this.mapProductToSummaryProductRes(item))
                        .toList();
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
                product.getProductImageURLs(),
                product.getPromotionImageURLs(),
                product.getProductName(),
                product.getSellingPrice(),
                product.getDiscount(),
                product.getQuantitySold(),
                product.getIsActive(),
                product.getCreatedAt(),
                product.getUpdatedAt());

    }

    public ProductDetailRes mapProductToProductDetailRes(Product product) {
        ProductDetailRes productRes = new ProductDetailRes();

        productRes.setId(product.getId());
        productRes.setSkuCode(product.getSkuCode());
        productRes.setImageURLs(product.getProductImageURLs());
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
