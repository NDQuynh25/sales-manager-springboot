package com.example.sales_manager.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.sales_manager.dto.request.ReqPermissionDto;
import com.example.sales_manager.entity.Permission;
import com.example.sales_manager.exception.DataNotFoundException;
import com.example.sales_manager.repository.PermissionRepsitory;

import jakarta.persistence.EntityManager;

@Service
public class PermissionService {

    private final PermissionRepsitory permissionRepsitory;

    private final EntityManager entityManager;

    public PermissionService(PermissionRepsitory permissionRepsitory, EntityManager entityManager) {
        this.permissionRepsitory = permissionRepsitory;
        this.entityManager = entityManager;
    }

    public Permission handleGetPermissionById(Long id) throws Exception {
        Permission permission = this.permissionRepsitory.findById(id).orElseThrow(() -> new DataNotFoundException("Permission not found"));

        return permission;
    }

    public List<Permission> handleGetPermissions(Pageable pageable) throws Exception {
        return permissionRepsitory.findAll();
    }

    public Permission handleCreatePermission(ReqPermissionDto reqPermissionDto) throws Exception {
        Permission Permission = new Permission();
        Permission.setName(reqPermissionDto.getName());
        return permissionRepsitory.save(Permission);
    }

    public Permission handleUpdatePermission(Long id, ReqPermissionDto reqPermissionDto) throws Exception {
        Permission Permission = permissionRepsitory.findById(id).orElseThrow(null);

        if (Permission == null) {
            throw new DataNotFoundException("Permission not found");
        }
        Permission.setName(reqPermissionDto.getName());
        entityManager.flush(); // Đảm bảo các thay đổi được đẩy xuống cơ sở dữ liệu
        return permissionRepsitory.save(Permission);
    }

    public boolean handleDeletePermission(Long id) throws Exception {
        if (!permissionRepsitory.existsById(id)) {
            throw new DataNotFoundException("Permission not found");
        }
        permissionRepsitory.deleteById(id);
        return true;
    } 
    
}
