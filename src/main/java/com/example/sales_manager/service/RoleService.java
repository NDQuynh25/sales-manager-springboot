package com.example.sales_manager.service;

import com.example.sales_manager.dto.PermissionDto;
import com.example.sales_manager.dto.ResultPagination;
import com.example.sales_manager.dto.RoleDto;
import com.example.sales_manager.dto.request.RoleReq;
import com.example.sales_manager.dto.response.PermissionRes;
import com.example.sales_manager.dto.response.RoleRes;
import com.example.sales_manager.entity.Permission;
import com.example.sales_manager.entity.Role;
import com.example.sales_manager.exception.DataNotFoundException;
import com.example.sales_manager.repository.RoleRepository;
import com.example.sales_manager.util.constant.RoleEnum;

import org.springframework.data.jpa.domain.Specification;

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

    public RoleService(RoleRepository roleRepository, EntityManager entityManager,
            PermissionService permissionService) {
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

    public ResultPagination handleGetRoles(Specification<Role> spec, Pageable pageable) throws Exception {
        Page<Role> page = roleRepository.findAll(spec, pageable);

        if (page.isEmpty()) {
            throw new DataNotFoundException("Role does not exist");
        }

        List<RoleRes> roles = page.getContent().stream().map(item -> this.mapRoleToRoleRes(item)).toList();

        ResultPagination resultPagination = new ResultPagination();
        ResultPagination.Meta meta = new ResultPagination.Meta();

        meta.setPage(page.getNumber());
        meta.setPageSize(page.getSize());
        meta.setTotalPages(page.getTotalPages());
        meta.setTotalElements(page.getTotalElements());

        resultPagination.setMeta(meta);
        resultPagination.setResult(roles);

        return resultPagination;
    }

    public Role handleCreateRole(RoleReq RoleReq) throws Exception {
        Role role = new Role();
        role.setRoleName(RoleReq.getRoleName());
        Set<Permission> permissions = new HashSet<>();
        if (RoleReq.getPermissionIds() != null) {
            for (Long permissionId : RoleReq.getPermissionIds()) {
                Permission permission = permissionService.handleGetPermissionById(permissionId);
                permissions.add(permission);
            }
        }
        role.setPermissions(permissions);
        return roleRepository.save(role);
    }

    @Transactional
    public Role handleUpdateRole(Long id, RoleReq RoleReq) throws Exception {
        Role role = roleRepository.findById(id).orElseThrow(null);

        if (role == null) {
            throw new DataNotFoundException("Role not found");
        }
        role.setRoleName(RoleReq.getRoleName());
        role.setIsActive(RoleReq.getIsActive());

        Set<Permission> permissions = new HashSet<>();

        if (RoleReq.getPermissionIds() != null) {
            for (Long permissionId : RoleReq.getPermissionIds()) {
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
    public Role handleGetDefaultRole() throws Exception {
        Role defaultRole = roleRepository.findByRoleName(RoleEnum.USER);
        if (defaultRole == null) {
            throw new DataNotFoundException("Default role does not exist");
        }
        return defaultRole;
    }

    public RoleRes mapRoleToRoleRes(Role role) {
        RoleRes roleRes = new RoleRes();
        roleRes.setId(role.getId());
        roleRes.setRoleName(role.getRoleName());
        roleRes.setIsActive(role.getIsActive());

        List<PermissionRes> PermissionRes = role.getPermissions()
                .stream()
                .map(item -> permissionService.mapPermissionToPermissionRes(item))
                .toList();

        roleRes.setCreatedBy(role.getCreatedBy());
        roleRes.setUpdatedBy(role.getUpdatedBy());
        roleRes.setCreatedAt(role.getCreatedAt());
        roleRes.setUpdatedAt(role.getUpdatedAt());

        roleRes.setPermissions(new HashSet<PermissionRes>(PermissionRes));
        return roleRes;
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
