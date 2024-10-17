package com.example.sales_manager.service;

import org.springframework.stereotype.Service;
import com.example.sales_manager.repository.CategoryRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.DataAccessException;
import com.example.sales_manager.entity.Category;

/* 
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public void handleAddCategory(String name, Integer is_active) {
        try {
            categoryRepository.save(new Category(name, is_active));
        } catch (DataAccessException e) {
            System.err.println("Error accessing data while adding category with name: " + name);
            // Xử lý các lỗi truy cập dữ liệu khác (ví dụ: ghi log hoặc thông báo cho người dùng)
        } catch (Exception e) {
            System.err.println("Unexpected error while adding category with name: " + name);
            // Xử lý các ngoại lệ không mong đợi khác
        }
    }
  
    public void deleteCategory(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            System.err.println("No category found with id: " + id);
            // Xử lý ngoại lệ khi không tìm thấy đối tượng để xóa (ví dụ: ghi log hoặc thông báo cho người dùng)
        } catch (DataAccessException e) {
            System.err.println("Error accessing data while deleting category with id: " + id);
            // Xử lý các lỗi truy cập dữ liệu khác (ví dụ: ghi log hoặc thông báo cho người dùng)
        } catch (Exception e) {
            System.err.println("Unexpected error while deleting category with id: " + id);
            // Xử lý các ngoại lệ không mong đợi khác
        }
    }
    

}
    */
