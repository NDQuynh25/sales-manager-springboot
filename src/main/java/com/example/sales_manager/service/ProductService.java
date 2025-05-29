package com.example.sales_manager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.sales_manager.dto.ResultPagination;
import com.example.sales_manager.dto.response.ProductDetailRes;

import com.example.sales_manager.dto.response.SummaryProductRes;
import com.example.sales_manager.entity.Category;
import com.example.sales_manager.entity.Product;
import com.example.sales_manager.entity.SKU;
import com.example.sales_manager.exception.DataNotFoundException;
import com.example.sales_manager.mapper.ProductMapper;
import com.example.sales_manager.repository.ProductRepository;
import com.example.sales_manager.repository.SkuRepository;
import com.fasterxml.jackson.annotation.JsonView;
import com.example.sales_manager.dto.request.ProductReq;
import com.example.sales_manager.dto.request.SkuReq;
import jakarta.transaction.Transactional;

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

    private final ProductMapper productMapper;

    public ProductService(
            ProductRepository productRepository,
            @Lazy CategoryService categoryService,
            FileService fileService,
            SkuService skuService,
            SkuRepository skuRepository,
            ProductMapper productMapper) {
        this.productRepository = productRepository;
        // this.categoryService = categoryService;
        this.fileService = fileService;
        this.skuService = skuService;
        this.skuRepository = skuRepository;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
    }

    // @Transactional
    @Transactional
    public Product handleCreateProduct(ProductReq productReq) throws Exception {
        Product product = new Product();
        try {

            // Tạo sản phẩm
            product = createProductEntity(productReq, product);

            // Lưu sản phẩm
            Product savedProduct = productRepository.save(product);

            // Lưu các SKU
            saveSkus(productReq.getSkus(), savedProduct);

            return savedProduct;
        } catch (Exception e) {
            // Xóa các file đã upload nếu có lỗi
            if (product.getProductImageURLs() != null)
                fileService.handleDeleteMultipleFiles(product.getProductImageURLs().toArray(new String[0]));
            if (product.getPromotionImageURLs() != null)
                fileService.handleDeleteMultipleFiles(product.getPromotionImageURLs().toArray(new String[0]));
            if (product.getDescriptionImageURLs() != null)
                fileService.handleDeleteMultipleFiles(product.getDescriptionImageURLs().toArray(new String[0]));

            LoggerFactory.getLogger(ProductService.class).error("Error creating product", e);
            throw new Exception("Error creating product: " + e.getMessage(), e);
        }
    }

    private Product createProductEntity(ProductReq productReq, Product product) throws Exception {
        try {
            List<String> productImageURLs = null;
            List<String> promotionImageURLs = null;
            List<String> descriptionImageURLs = new ArrayList<>();

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
            if (productReq.getDescriptionImages() != null && !productReq.getDescriptionImages().isEmpty()) {
                for (MultipartFile file : productReq.getDescriptionImages()) {
                    String newImageUrl = fileService.handleUploadFile(file);
                    descriptionImageURLs.add(newImageUrl);
                    
                    String oldImageUrl = "https://example.com/" + file.getOriginalFilename() + ".jpg";

                    newDescription = newDescription.replace(oldImageUrl, newImageUrl);
                }
                //System.out.println("[INFO] newDescription: " + newDescription);
            }
            // Set values ​​to product attributes
            product.setProductImageURLs(productImageURLs);
            product.setPromotionImageURLs(promotionImageURLs);
            product.setDescriptionImageURLs(descriptionImageURLs);

            product.setProductName(productReq.getProductName());
            product.setDescription(newDescription);
            product.setBrand(productReq.getBrand());
            product.setCountryOfOrigin(productReq.getCountryOfOrigin());
            product.setMaterials(productReq.getMaterials());
            product.setQuantitySold(0);

            // Set variations and options
            if (productReq.getVariation1() != null && productReq.getOptions1() != null
                    && !productReq.getOptions1().isEmpty()) {
                product.setVariation1(productReq.getVariation1());
                product.setOptions1(productReq.getOptions1());
            }
            if (productReq.getVariation2() != null && productReq.getOptions2() != null
                    && !productReq.getOptions2().isEmpty()) {
                product.setVariation2(productReq.getVariation2());
                product.setOptions2(productReq.getOptions2());
            }

            // Set categories
            List<Category> categories = new ArrayList<>();
            for (Long categoryId : productReq.getCategoryIds()) {
                Category category = categoryService.handleGetCategoryById(categoryId);
                if (category != null && category.getIsActive() == 1) {
                    categories.add(category);

                    System.out.println("[INFO] category: " + category.getCategoryName());
                }

            }

            product.setCategories(categories);

            return product;
        } catch (Exception e) {
            if (product.getProductImageURLs() != null)
                fileService.handleDeleteMultipleFiles(product.getProductImageURLs().toArray(new String[0]));
            if (product.getPromotionImageURLs() != null)
                fileService.handleDeleteMultipleFiles(product.getPromotionImageURLs().toArray(new String[0]));
            if (product.getDescriptionImageURLs() != null)
                fileService.handleDeleteMultipleFiles(product.getDescriptionImageURLs().toArray(new String[0]));
            throw new Exception("Error creating product entity: " + e.getMessage(), e);
        }
    }

    private void saveSkus(List<SkuReq> skus, Product product) throws Exception {
        List<SKU> listSkus = new ArrayList<>();
        for (SkuReq skuReq : skus) {
            if (skuReq.getId() == null) {
                SKU sku = skuRepository.findSKUById(skuReq.getId());
                if (sku != null) {
                    sku.setOption1(skuReq.getOption1());
                    sku.setOption2(skuReq.getOption2());
                    sku.setOriginalPrice(skuReq.getOriginalPrice());
                    sku.setSellingPrice(skuReq.getSellingPrice());
                    sku.setStock(skuReq.getStock());
                    sku.setDiscount(skuReq.getDiscount());
                    sku.setIsActive(skuReq.getIsActive());
                    sku.setProduct(product);
                    listSkus.add(sku);
                    continue; // Skip to next skuReq if id is not null
                } 
            }
           
            SKU newSku = SKU.builder()
                    .option1(skuReq.getOption1())
                    .option2(skuReq.getOption2())
                    .originalPrice(skuReq.getOriginalPrice())
                    .sellingPrice(skuReq.getSellingPrice())
                    .stock(skuReq.getStock())
                    .discount(skuReq.getDiscount())
                    .product(product)
                    .build();
            listSkus.add(newSku);
        }
        skuRepository.saveAll(listSkus);
    }

    @Transactional
    public Product handleUpdateProduct(Long id, ProductReq productReq) throws Exception {

        if (productReq.getId() == null || !productReq.getId().equals(id)) {
            throw new IllegalArgumentException("Product ID mismatch");
        }

        Product product;
        try {
            product = productRepository.findProductById(productReq.getId());

            if (product == null) {
                throw new DataNotFoundException("Product not found");
            }

            // Update product entity
            product = createProductEntity(productReq, product);

            // Update SKUs
            saveSkus(productReq.getSkus(), product);

            return product;
        } catch (Exception e) {
            throw new Exception("Error uploading promotion images: " + e.getMessage(), e);
        }
    }

    public Product handleGetProductById(Long id) throws Exception {
        try {
            return productRepository.findProductById(id);
        } catch (Exception e) {
            throw new Exception("Error getting product by id");
        }
    }

    public ResultPagination handleGetProducts(Specification<Product> spec, Pageable pageable, String view)
            throws Exception {

        Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        Boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        try {
            ResultPagination resultPagination = new ResultPagination();
            ResultPagination.Meta meta = resultPagination.new Meta();

            if (isAdmin) {
                spec = spec.and((root, query, cb) -> cb.equal(root.get("isActive"), 0));
            }
            // Page<Product> productss = productRepository.findAll(spec, pageable);
            // System.out.println(
            // "[INFO] productss: " +
            // productss.getContent().get(1).getCategories().get(0).getCategoryName());

            Page<ProductDetailRes> products = productMapper
                    .mapToProductDetailResPage(productRepository.findAll(spec, pageable));

            if (products.isEmpty()) {
                throw new DataNotFoundException("No products found");
            }
            resultPagination.setResult(products.getContent());

            meta.setPage(products.getNumber());
            meta.setPageSize(products.getSize());
            meta.setTotalPages(products.getTotalPages());
            meta.setTotalElements(products.getTotalElements());
            resultPagination.setMeta(meta);

            return resultPagination;

        } catch (Exception e) {
            LoggerFactory.getLogger(ProductService.class).error("Error getting products", e); // log to see root cause
            throw new Exception("Error getting products", e);
        }
    }

    public Long handleCountProductsByCategoryId(Long categoryId) throws Exception {
        try {
            return productRepository.countProductsByCategoryId(categoryId);
        } catch (Exception e) {
            throw new Exception("Error counting products by category id");

        }
    }

}
