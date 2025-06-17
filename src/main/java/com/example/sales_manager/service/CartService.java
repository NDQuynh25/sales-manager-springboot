package com.example.sales_manager.service;

import org.springframework.stereotype.Service;

import com.example.sales_manager.entity.Cart;
import com.example.sales_manager.repository.CartItemRepository;
import com.example.sales_manager.repository.CartRepository;
import com.example.sales_manager.repository.ProductRepository;
import com.example.sales_manager.repository.SkuRepository;
import com.example.sales_manager.dto.ResultPagination;
import com.example.sales_manager.dto.request.CartItemReq;
import com.example.sales_manager.dto.response.CartRes;
import com.example.sales_manager.entity.CartItem;
import com.example.sales_manager.entity.Product;
import com.example.sales_manager.entity.SKU;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.sales_manager.entity.User;
import com.example.sales_manager.repository.UserRepository;
import com.example.sales_manager.mapper.CartItemMapper;
import com.example.sales_manager.mapper.CartMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final SkuRepository skuRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;
    private final EntityManager entityManager;

    public CartService(CartRepository cartRepository, UserRepository userRepository, SkuRepository skuRepository, ProductRepository productRepository, CartMapper cartMapper, CartItemMapper cartItemMapper, CartItemRepository cartItemRepository, EntityManager entityManager) {

        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.skuRepository = skuRepository;
        this.productRepository = productRepository;
        this.cartMapper = cartMapper;
        this.cartItemMapper = cartItemMapper;
        this.cartItemRepository = cartItemRepository;
        this.entityManager = entityManager;

    }

    private List<Object> initCartItemData (Long userId, CartItemReq cartItemReq) {
        try {
            if (userId == null || userId <= 0) {
                throw new IllegalArgumentException("User ID must be provided and greater than 0");
            }

            if (cartItemReq == null || cartItemReq.getQuantity() <= 0) {
                throw new IllegalArgumentException("Invalid cart item request");
            }

            User user = userRepository.findByIdAndIsActive(userId, 1);
            if (user == null) {
                throw new EntityNotFoundException("User not found with ID: " + userId);
            }

            Cart cart = user.getCart();

            if (cart == null) {
                cart = Cart.builder()
                        .user(user)
                        .build();
                user.setCart(cart);
                userRepository.save(user);
            }
            if (cart.getId() != cartItemReq.getCartId()) {
                throw new IllegalArgumentException("Cart ID in request does not match user's cart ID");
            }

            
            Product product = productRepository.findProductByIdAndIsActive(cartItemReq.getProductId(), 1);
            if (product == null) {
                System.out.println("[INFO] Product not found with ID: " + cartItemReq.getProductId() );
                throw new EntityNotFoundException("Product not found");
            }

            Optional<SKU> existingSKU = product.getSkus().stream()
                    .filter(sku -> 
                        sku.getId().equals(cartItemReq.getSkuId()) && 
                        sku.getIsActive() == 1 &&
                        sku.getOption1().equals(cartItemReq.getOption1()) &&
                        sku.getOption2().equals(cartItemReq.getOption2())
                    )
                    .findFirst();
            
            SKU sku = existingSKU.orElseThrow(() -> new EntityNotFoundException("SKU not found or has been changed"));

            

            return List.of(cart, product, sku);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    

    @Transactional
    public CartRes addItemToCart(Long userId, CartItemReq cartItemReq) {
        try {
            List<Object> cartItemData = initCartItemData(userId, cartItemReq);
        
            Cart cart = (Cart) cartItemData.get(0);
            Product product = (Product) cartItemData.get(1);
            SKU sku = (SKU) cartItemData.get(2);
           
            Optional<CartItem> existingItem = cart.getCartItems().stream()
                    .filter(item -> item.getSku().getId().equals(cartItemReq.getSkuId()) 
                                    
                    )
                    .findFirst();

            if (existingItem.isPresent()) {
                Long quantity = existingItem.get().getQuantity() + (long) cartItemReq.getQuantity();
                if(sku.getStock() < quantity || sku.getStock() <= 0 ) {
                    System.out.println("[INFO] Insufficient stock for SKU: " + sku.getId());
                    throw new IllegalArgumentException("Insufficient stock for SKU");
                }
                existingItem.get().setQuantity(quantity.intValue());
            } else {
                CartItem cartItem = CartItem.builder()
                        .sku(sku)
                        .product(product)
                        .quantity(cartItemReq.getQuantity())
                        .cart(cart)
                        .build();
                
                cart.getCartItems().add(cartItem);
            }

            return cartMapper.mapToCartRes(cartRepository.save(cart));

        } catch (Exception e) {
            
            throw new RuntimeException(e.getMessage(), e);
        }   
    }

    @Transactional
    public CartRes updateCartItem(Long userId, Long cartItemId, CartItemReq cartItemReq) {
        try {
            List<Object> cartItemData = initCartItemData(userId, cartItemReq);
        
            Cart cart = (Cart) cartItemData.get(0);
            Product product = (Product) cartItemData.get(1);
            SKU sku = (SKU) cartItemData.get(2);

            // Check if the cart item exists in the cart
            Optional<CartItem> existingItem = cart.getCartItems().stream()
                    .filter(item -> item.getId().equals(cartItemId))
                    .findFirst();

            if (existingItem.isEmpty()) {
                throw new EntityNotFoundException("Cart item not found");
            }
           
            if (sku.getStock() < cartItemReq.getQuantity() || sku.getStock() <= 0) {
                System.out.println("[INFO] Insufficient stock for SKU: " + sku.getId());
                throw new IllegalArgumentException("Insufficient stock for SKU");
            }

            // Check if there is an existing cart item with the same SKU but different ID
            Optional<CartItem> existingCartItemWithSku = cart.getCartItems().stream()
                    .filter(item -> item.getSku().getId().equals(cartItemReq.getSkuId()) && 
                                    item.getSku().getOption1().equals(cartItemReq.getOption1()) &&
                                    item.getSku().getOption2().equals(cartItemReq.getOption2()))
                   
                    .findFirst();
            
            // If there is an existing cart item with the same SKU but different ID, delete it
            if (existingCartItemWithSku.isPresent() && !existingCartItemWithSku.get().getId().equals(cartItemId)) {
                cart.getCartItems().remove(existingCartItemWithSku.get()); //  remove the item from the cart, avoid errors ObjectDeletedException
                cartItemRepository.delete(existingCartItemWithSku.get());
                entityManager.flush();

            }
            // Check if the SKU has sufficient stock for the requested quantity
            if (sku.getStock() < cartItemReq.getQuantity() || sku.getStock() <= 0) {
                System.out.println("[INFO] Insufficient stock for SKU: " + sku.getId());
                throw new IllegalArgumentException("Insufficient stock for SKU;");
            }
            
            // Update the existing cart item with the new quantity and SKU
            existingItem.get().setQuantity(cartItemReq.getQuantity());
            existingItem.get().setSku(sku);
            existingItem.get().setProduct(product);
            existingItem.get().setCart(cart);
           
            //cartItemRepository.save(existingItem.get());

            return cartMapper.mapToCartRes(cartRepository.save(cart));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public ResultPagination getCartByUserId(Long userId, Specification<CartItem> spec, Pageable pageable) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be provided and greater than 0");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        Cart cart = user.getCart();
        if (cart == null) {
            throw new EntityNotFoundException("Cart not found for user with ID: " + userId);
        }

        // specification to filter cart items by cart ID
        Specification<CartItem> cartItemSpec = (root, query, criteriaBuilder) -> criteriaBuilder.and(
            criteriaBuilder.equal(root.get("cart").get("id"), cart.getId()),
            criteriaBuilder.equal(root.get("isActive"), 1)
        );


        Page<CartItem> cartItemsPage = cartItemRepository.findAll(cartItemSpec, pageable);


        ResultPagination resultPagination = ResultPagination.builder()
                .meta(ResultPagination.Meta.builder()
                        .page(cartItemsPage.getNumber())
                        .pageSize(cartItemsPage.getSize())
                        .totalElements(cartItemsPage.getTotalElements())
                        .totalPages(cartItemsPage.getTotalPages())
                        .build())
                .result(cartItemsPage.getContent().stream()
                        .map(cartItemMapper::mapToCartItemRes)
                        .toList())
                .build();

        return resultPagination;
    }

          

}
