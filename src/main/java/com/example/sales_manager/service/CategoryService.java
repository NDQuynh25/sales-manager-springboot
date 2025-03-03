package com.example.sales_manager.service;

import org.springframework.stereotype.Service;
import com.example.sales_manager.repository.CategoryRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.dao.DataAccessException;

import com.example.sales_manager.dto.ResultPagination;
import com.example.sales_manager.dto.request.ReqCategoryDto;
import com.example.sales_manager.dto.response.ResCategoryDto;
import com.example.sales_manager.dto.response.ResUserDto;
import com.example.sales_manager.entity.Category;
import com.example.sales_manager.entity.User;
import com.example.sales_manager.exception.DataNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private final ProductService productService;

    public CategoryService(CategoryRepository categoryRepository, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }

    public Category handleGetCategoryById(Long id) throws Exception {
        try {
            return categoryRepository.findCategoryById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new DataNotFoundException("Category with id " + id + " not found");
        } catch (DataAccessException e) {
            throw new DataNotFoundException("Category with id " + id + " not found");
        }   
    }

    public Category handleCreateCategory(ReqCategoryDto reqCategoryDto) {
        try {
            Category category = new Category();
            category.setCategoryName(reqCategoryDto.getCategoryName());
            List<Category> subCategories = new ArrayList<>();

            if (reqCategoryDto.getCategoryIds() == null) {
                return categoryRepository.save(category);
            }

            for (Long id : reqCategoryDto.getCategoryIds()) {
                Category subCategory = categoryRepository.findCategoryById(id);
                subCategories.add(subCategory);
                
            }
            return categoryRepository.save(category);

        } catch (DataAccessException e) {

            throw new DataNotFoundException("Error creating category");
        }
       
    }

 
    public ResultPagination handleGetCategories(Specification<Category> spec, Pageable pageable) throws Exception{
       
        try {
            Page<Category> page = categoryRepository.findAll(spec, pageable);
            if (page.isEmpty()) {
                throw new DataNotFoundException("No category found");
            }

            ResultPagination resultPagination = new ResultPagination();
            ResultPagination.Meta meta = resultPagination.new Meta();
            
            List<ResCategoryDto> categories = page.getContent().stream().map(item -> {
                try {
                    return this.mapCategoryToResCategoryDto(item);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }).toList();
            

            meta.setPage(page.getNumber());
            meta.setPageSize(page.getSize());
            meta.setTotalPages(page.getTotalPages());
            meta.setTotalElements(page.getTotalElements());

            resultPagination.setMeta(meta);
            resultPagination.setResult(categories);
            return resultPagination;

        } catch (DataAccessException e) {
            throw new DataNotFoundException("Error getting categories");
        }  
       
    }

    public ResCategoryDto mapCategoryToResCategoryDto(Category category) throws Exception {
        return new ResCategoryDto(
            category.getId(),
            category.getCategoryName(),
            productService.handleCountProductsByCategoryId(category.getId()),
            category.getSubCategories(),
            category.getIsActive(),
            category.getCreatedBy(),
            category.getUpdatedBy(),
            category.getCreatedAt(),
            category.getUpdatedAt()
        );
    }

    

}
    
