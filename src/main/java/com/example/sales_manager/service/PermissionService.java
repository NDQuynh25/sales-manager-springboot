package com.example.sales_manager.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.sales_manager.dto.PermissionDto;
import com.example.sales_manager.dto.ResultPagination;
import com.example.sales_manager.dto.request.PermissionReq;
import com.example.sales_manager.dto.response.PermissionRes;
import com.example.sales_manager.entity.Permission;
import com.example.sales_manager.exception.DataNotFoundException;
import com.example.sales_manager.repository.PermissionRepsitory;

import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PermissionService {

    private final PermissionRepsitory permissionRepsitory;

    private final EntityManager entityManager;

    public PermissionService(PermissionRepsitory permissionRepsitory, EntityManager entityManager) {
        this.permissionRepsitory = permissionRepsitory;
        this.entityManager = entityManager;
    }

    public Permission handleGetPermissionById(Long id) throws Exception {
        Permission permission = this.permissionRepsitory.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Permission not found"));

        return permission;
    }

    public ResultPagination handleGetPermissions(Pageable pageable) throws Exception {

        Page<Permission> page = permissionRepsitory.findAll(pageable);

        if (page.isEmpty()) {
            throw new DataNotFoundException("No permission found");
        }
        List<PermissionRes> permissions = page.getContent().stream()
                .map(item -> this.mapPermissionToPermissionRes(item)).toList();

        ResultPagination resultPagination = new ResultPagination();
        ResultPagination.Meta meta = resultPagination.new Meta();

        meta.setPage(page.getNumber());
        meta.setPageSize(page.getSize());
        meta.setTotalPages(page.getTotalPages());
        meta.setTotalElements(page.getTotalElements());

        resultPagination.setMeta(meta);
        resultPagination.setResult(permissions);

        return resultPagination;

    }

    public Permission handleCreatePermission(PermissionReq PermissionReq) throws Exception {
        Permission permission = new Permission();
        permission.setPermissionName(PermissionReq.getPermissionName());
        permission.setApiAccess(PermissionReq.getApiAccess());
        permission.setMethod(PermissionReq.getMethod());
        permission.setDescription(PermissionReq.getDescription());
        permission.setIsActive(PermissionReq.getIsActive());
        return permissionRepsitory.save(permission);
    }

    @Transactional
    public Permission handleUpdatePermission(Long id, PermissionReq PermissionReq) throws Exception {
        Permission permission = permissionRepsitory.findById(id).orElseThrow(null);

        if (permission == null) {
            throw new DataNotFoundException("Permission not found");
        }
        permission.setPermissionName(PermissionReq.getPermissionName());
        permission.setApiAccess(PermissionReq.getApiAccess());
        permission.setMethod(PermissionReq.getMethod());
        permission.setDescription(PermissionReq.getDescription());
        permission.setIsActive(PermissionReq.getIsActive());
        entityManager.flush();
        return permissionRepsitory.save(permission);
    }

    public boolean handleDeletePermission(Long id) throws Exception {
        if (!permissionRepsitory.existsById(id)) {
            throw new DataNotFoundException("Permission not found");
        }
        permissionRepsitory.deleteById(id);
        return true;
    }

    public PermissionRes mapPermissionToPermissionRes(Permission permission) {
        PermissionRes PermissionRes = new PermissionRes();
        PermissionRes.setId(permission.getId());
        PermissionRes.setPermissionName(permission.getPermissionName());
        PermissionRes.setApiAccess(permission.getApiAccess());
        PermissionRes.setMethod(permission.getMethod());
        PermissionRes.setDescription(permission.getDescription());
        PermissionRes.setIsActive(permission.getIsActive());
        PermissionRes.setCreatedAt(permission.getCreatedAt());
        PermissionRes.setCreatedBy(permission.getCreatedBy());
        PermissionRes.setUpdatedAt(permission.getUpdatedAt());
        PermissionRes.setUpdatedBy(permission.getUpdatedBy());

        return PermissionRes;
    }

    public PermissionDto mapPermissionToPermissionDto(Permission permission) {
        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setId(permission.getId());
        permissionDto.setPermissionName(permission.getPermissionName());
        permissionDto.setApiAccess(permission.getApiAccess());
        permissionDto.setMethod(permission.getMethod());

        return permissionDto;
    }

}
