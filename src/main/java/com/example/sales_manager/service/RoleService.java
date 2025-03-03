package com.example.sales_manager.service;

import com.example.sales_manager.dto.PermissionDto;
import com.example.sales_manager.dto.ResultPagination;
import com.example.sales_manager.dto.RoleDto;
import com.example.sales_manager.dto.request.ReqRoleDto;
import com.example.sales_manager.dto.response.ResPermissionDto;
import com.example.sales_manager.dto.response.ResRoleDto;
import com.example.sales_manager.entity.Permission;
import com.example.sales_manager.entity.Role;
import com.example.sales_manager.exception.DataNotFoundException;
import com.example.sales_manager.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    private final PermissionService permissionService;

    private final EntityManager entityManager;

    public RoleService(RoleRepository roleRepository, EntityManager entityManager, PermissionService permissionService) {
        this.roleRepository = roleRepository;
        this.entityManager = entityManager;
        this.permissionService = permissionService;
    }

    public Role handleGetRoleById(Long id) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("Role ID must not be null");
        }

        return roleRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Role does not exist"));
    }

    public ResultPagination handleGetRoles(Pageable pageable) throws Exception {
        Page<Role> page = roleRepository.findAll(pageable);

        if (page.isEmpty()) {
            throw new DataNotFoundException("Role does not exist");
        }
        
        List<ResRoleDto> roles = page.getContent().stream().map(item -> this.mapRoleToResRoleDto(item)).toList();

        ResultPagination resultPagination = new ResultPagination();
        ResultPagination.Meta meta = resultPagination.new Meta();

        meta.setPage(page.getNumber());
        meta.setPageSize(page.getSize());
        meta.setTotalPages(page.getTotalPages());
        meta.setTotalElements(page.getTotalElements());
        
        resultPagination.setMeta(meta);
        resultPagination.setResult(roles); 

        return resultPagination;
    }

    public Role handleCreateRole(ReqRoleDto reqRoleDto) throws Exception {
        Role role = new Role();
        role.setRoleName(reqRoleDto.getRoleName());
        Set<Permission> permissions = new HashSet<>();
        if (reqRoleDto.getPermissionIds() != null) {
            for (Long permissionId : reqRoleDto.getPermissionIds()) {
                Permission permission = permissionService.handleGetPermissionById(permissionId);
                permissions.add(permission);
            }
        }
        role.setPermissions(permissions);
        return roleRepository.save(role);
    }

    @Transactional
    public Role handleUpdateRole(Long id, ReqRoleDto reqRoleDto) throws Exception {
        Role role = roleRepository.findById(id).orElseThrow(null);

        if (role == null) {
            throw new DataNotFoundException("Role not found");
        }
        role.setRoleName(reqRoleDto.getRoleName());
        role.setIsActive(reqRoleDto.getIsActive());

        Set<Permission> permissions = new HashSet<>();

        if (reqRoleDto.getPermissionIds() != null) {
            for (Long permissionId : reqRoleDto.getPermissionIds()) {
                if (permissionId != null) {
                    Permission permission = permissionService.handleGetPermissionById(permissionId);
                    permissions.add(permission);
                }
            }
        }
        role.setPermissions(permissions);

        entityManager.flush(); // Đảm bảo các thay đổi được đẩy xuống cơ sở dữ liệu
        return roleRepository.save(role);
    }

    public boolean handleDeleteRole(Long id) throws Exception {
        if (!roleRepository.existsById(id)) {
            throw new DataNotFoundException("Role not found");
        }
        roleRepository.deleteById(id);
        return true;
    }

    public ResRoleDto mapRoleToResRoleDto(Role role) {
        ResRoleDto resRoleDto = new ResRoleDto();
        resRoleDto.setId(role.getId());
        resRoleDto.setRoleName(role.getRoleName());
        resRoleDto.setIsActive(role.getIsActive());

        List<ResPermissionDto> resPermissionDto = role.getPermissions()
            .stream()
            .map(item -> permissionService.mapPermissionToResPermissionDto(item))
            .toList();

        resRoleDto.setCreatedBy(role.getCreatedBy());
        resRoleDto.setUpdatedBy(role.getUpdatedBy());
        resRoleDto.setCreatedAt(role.getCreatedAt());
        resRoleDto.setUpdatedAt(role.getUpdatedAt());
        
        resRoleDto.setPermissions(new HashSet<ResPermissionDto>(resPermissionDto));
        return resRoleDto;
    }

    public RoleDto mapRoleToRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setRoleName(role.getRoleName());
        List<PermissionDto> permissionDtos = role.getPermissions()
            .stream()
            .map(item -> permissionService.mapPermissionToPermissionDto(item))
            .toList();

        roleDto.setPermissions(new HashSet<PermissionDto>(permissionDtos));

        return roleDto;
    }


    
}
