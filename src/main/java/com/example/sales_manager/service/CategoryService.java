package com.example.sales_manager.service;

import org.springframework.stereotype.Service;
import com.example.sales_manager.repository.CategoryRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.dao.DataAccessException;

import com.example.sales_manager.dto.ResultPagination;
import com.example.sales_manager.dto.request.CategoryReq;
import com.example.sales_manager.dto.response.CategoryRes;
import com.example.sales_manager.dto.response.SummaryCategoryRes;
import com.example.sales_manager.entity.Category;

import com.example.sales_manager.exception.DataNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
            Category category = categoryRepository.findCategoryById(id);

            if (category == null) {
                throw new DataNotFoundException("Category with id " + id + " not found");
            }
            return category;

        } catch (EmptyResultDataAccessException e) {
            throw new DataNotFoundException("Category with id " + id + " not found");
        } catch (DataAccessException e) {
            throw new DataNotFoundException("Category with id " + id + " not found");
        } catch (NullPointerException e) {
            throw new DataNotFoundException("Category with id " + id + " not found");
        }

    }

    public Category handleCreateCategory(CategoryReq CategoryReq) {
        try {
            Category category = new Category();
            category.setCategoryName(CategoryReq.getCategoryName());
            List<Category> subCategories = new ArrayList<>();

            if (CategoryReq.getCategoryIds() == null) {
                return categoryRepository.save(category);
            }

            for (Long id : CategoryReq.getCategoryIds()) {
                Category subCategory = categoryRepository.findCategoryById(id);
                if (subCategory == null) {
                    throw new DataNotFoundException("Category with id " + id + " not found");
                } else if (subCategory.getIsActive() == 0) {
                    throw new DataNotFoundException("Category with id " + id + " is inactive");
                }
                if (id == category.getId()) {
                    throw new DataNotFoundException("Category with id " + id + " is the same as the parent category");
                }
                if (subCategory.getParentCategory() != null
                        && subCategory.getParentCategory().getId() != category.getId()) {
                    throw new DataNotFoundException(
                            "Category with id " + id + " is already a subcategory of another category");
                }

                subCategory.setParentCategory(category);
                subCategories.add(subCategory);

            }
            category.setSubCategories(subCategories);

            return categoryRepository.save(category);

        } catch (DataAccessException e) {

            throw new DataNotFoundException("Error creating category");
        }
    }

    public Category handleUpdateCategory(Long id, CategoryReq categoryReq) throws Exception {
        try {
            Category category = categoryRepository.findCategoryById(id);

            if (category == null) {
                throw new DataNotFoundException("Category with id " + id + " not found");
            }

            // Cập nhật các thông tin cơ bản
            category.setCategoryName(categoryReq.getCategoryName());
            category.setIsActive(categoryReq.getIsActive());
            category.setParentCategory(category);

            // Xử lý cập nhật subcategories
            Set<Long> newSubCategoryIds = new HashSet<>(categoryReq.getCategoryIds());
            List<Category> updatedSubCategories = new ArrayList<>();

            for (Long subId : categoryReq.getCategoryIds()) {
                Category subCategory = categoryRepository.findCategoryById(subId);
                if (subCategory == null) {
                    throw new DataNotFoundException("Category with id " + subId + " not found");
                }
                if (subCategory.getIsActive() == 0) {
                    throw new DataNotFoundException("Category with id " + subId + " is inactive");
                }
                if (subId == category.getId()) {
                    throw new DataNotFoundException(
                            "Category with id " + subId + " is the same as the parent category");
                }
                if (category.getParentCategory() != null && subId == category.getParentCategory().getId()) {
                    throw new DataNotFoundException(
                            "Category with id " + subId + " is the same as the parent category");
                }
                if (subCategory.getParentCategory() != null
                        && subCategory.getParentCategory().getId() != category.getId()) {
                    throw new DataNotFoundException(
                            "Category with id " + subId + " is already a subcategory of another category");
                }

                subCategory.setParentCategory(category);
                updatedSubCategories.add(subCategory);
            }

            // Loại bỏ subcategories không còn thuộc danh sách mới
            for (Category existingSub : new ArrayList<>(category.getSubCategories())) {
                if (!newSubCategoryIds.contains(existingSub.getId())) {
                    existingSub.setParentCategory(null);
                }
            }

            return categoryRepository.save(category);

        } catch (DataAccessException e) {
            System.out.println("Error updating category: " + e.getMessage());
            throw new DataNotFoundException("Error updating category");
        } catch (Exception e) {

            throw new Exception(e.getMessage());
        }
    }

    public boolean handleDeleteCategory(Long id) throws Exception {
        try {
            Category category = categoryRepository.findCategoryById(id);

            if (category == null) {
                throw new DataNotFoundException("Category with id " + id + " not found");
            }

            // Xóa tất cả các subcategories của category
            for (Category sub : category.getSubCategories()) {
                sub.setParentCategory(null);
            }

            // Xóa category
            categoryRepository.deleteById(category.getId());

            return true;

        } catch (DataAccessException e) {
            throw new DataNotFoundException("Error deleting category");
        }
    }

    public ResultPagination handleGetCategories(Specification<Category> spec, Pageable pageable, Boolean isSummary)
            throws Exception {

        try {
            ResultPagination resultPagination = new ResultPagination();
            ResultPagination.Meta meta = new ResultPagination.Meta();
            Page<Category> page = categoryRepository.findAll(spec, pageable);
            if (page.isEmpty()) {
                throw new DataNotFoundException("No category found");
            }

            List<CategoryRes> categories = new ArrayList<>();
            List<SummaryCategoryRes> summaryCategories = new ArrayList<>();

            if (isSummary) {
                summaryCategories = page.getContent().stream().map(item -> {
                    try {
                        return this.mapCategoryToSummaryCategoryRes(item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }).toList();

                resultPagination.setResult(summaryCategories);

            } else {
                categories = page.getContent().stream().map(item -> {
                    try {
                        return this.mapCategoryToCategoryRes(item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }).toList();

                resultPagination.setResult(categories);
            }

            meta.setPage(page.getNumber());
            meta.setPageSize(page.getSize());
            meta.setTotalPages(page.getTotalPages());
            meta.setTotalElements(page.getTotalElements());

            resultPagination.setMeta(meta);

            return resultPagination;

        } catch (DataAccessException e) {
            throw new DataNotFoundException("Error getting categories");
        }

    }

    public CategoryRes mapCategoryToCategoryRes(Category category) throws Exception {
        return new CategoryRes(
                category.getId(),
                category.getCategoryName(),
                productService.handleCountProductsByCategoryId(category.getId()),
                Optional.ofNullable(category.getSubCategories())
                        .map(subCategories -> subCategories.stream()
                                .map(item -> {
                                    try {
                                        return this.mapCategoryToSummaryCategoryRes(item);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    return null;
                                })
                                .filter(Objects::nonNull)
                                .toList())
                        .orElse(Collections.emptyList()),
                category.getParentCategory() != null ? category.getParentCategory().getId() : null,
                category.getIsActive(),
                category.getCreatedBy(),
                category.getUpdatedBy(),
                category.getCreatedAt(),
                category.getUpdatedAt());

    }

    public SummaryCategoryRes mapCategoryToSummaryCategoryRes(Category category) throws Exception {
        return new SummaryCategoryRes(
                category.getId(),
                category.getCategoryName(),
                category.getIsActive());
    }

}
