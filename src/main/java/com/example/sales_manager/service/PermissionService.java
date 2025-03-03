package com.example.sales_manager.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.sales_manager.dto.PermissionDto;
import com.example.sales_manager.dto.ResultPagination;
import com.example.sales_manager.dto.request.ReqPermissionDto;
import com.example.sales_manager.dto.response.ResPermissionDto;
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
        Permission permission = this.permissionRepsitory.findById(id).orElseThrow(() -> new DataNotFoundException("Permission not found"));

        return permission;
    }

    public ResultPagination handleGetPermissions(Pageable pageable) throws Exception {
        
        Page<Permission> page = permissionRepsitory.findAll(pageable);
        
        if (page.isEmpty()) {
            throw new DataNotFoundException("No permission found");
        }
        List<ResPermissionDto> permissions = page.getContent().stream().map(item -> this.mapPermissionToResPermissionDto(item)).toList();

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

    public Permission handleCreatePermission(ReqPermissionDto reqPermissionDto) throws Exception {
        Permission permission = new Permission();
        permission.setPermissionName(reqPermissionDto.getPermissionName());
        permission.setApiAccess(reqPermissionDto.getApiAccess());
        permission.setMethod(reqPermissionDto.getMethod());
        permission.setDescription(reqPermissionDto.getDescription());
        permission.setIsActive(reqPermissionDto.getIsActive());
        return permissionRepsitory.save(permission);
    }

    @Transactional
    public Permission handleUpdatePermission(Long id, ReqPermissionDto reqPermissionDto) throws Exception {
        Permission permission = permissionRepsitory.findById(id).orElseThrow(null);

        if (permission == null) {
            throw new DataNotFoundException("Permission not found");
        }
        permission.setPermissionName(reqPermissionDto.getPermissionName());
        permission.setApiAccess(reqPermissionDto.getApiAccess());
        permission.setMethod(reqPermissionDto.getMethod());
        permission.setDescription(reqPermissionDto.getDescription());
        permission.setIsActive(reqPermissionDto.getIsActive());
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

    public ResPermissionDto mapPermissionToResPermissionDto(Permission permission) {
        ResPermissionDto resPermissionDto = new ResPermissionDto();
        resPermissionDto.setId(permission.getId());
        resPermissionDto.setPermissionName(permission.getPermissionName());
        resPermissionDto.setApiAccess(permission.getApiAccess());
        resPermissionDto.setMethod(permission.getMethod());
        resPermissionDto.setDescription(permission.getDescription());
        resPermissionDto.setIsActive(permission.getIsActive());
        resPermissionDto.setCreatedAt(permission.getCreatedAt());
        resPermissionDto.setCreatedBy(permission.getCreatedBy());
        resPermissionDto.setUpdatedAt(permission.getUpdatedAt());
        resPermissionDto.setUpdatedBy(permission.getUpdatedBy());
     
        return resPermissionDto;
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
