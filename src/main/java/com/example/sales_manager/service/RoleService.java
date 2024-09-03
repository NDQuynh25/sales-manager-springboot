package com.example.sales_manager.service;

import com.example.sales_manager.dto.ResultPagination;
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
        Role role = roleRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Role not found"));
        
        return role;
    }

    public ResultPagination handleGetRoles(Pageable pageable) throws Exception {
        Page<Role> page = roleRepository.findAll(pageable);

        if (page.isEmpty()) {
            throw new DataNotFoundException("No role found");
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
        role.setName(reqRoleDto.getName());
        return roleRepository.save(role);
    }

    public Role handleUpdateRole(Long id, ReqRoleDto reqRoleDto) throws Exception {
        Role role = roleRepository.findById(id).orElseThrow(null);

        if (role == null) {
            throw new DataNotFoundException("Role not found");
        }
        role.setName(reqRoleDto.getName());
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
        resRoleDto.setName(role.getName());
        resRoleDto.setIsActive(role.getIsActive());

        List<ResPermissionDto> resPermissionDto = role.getPermissions()
            .stream()
            .map(item -> permissionService.mapPermissionToResPermissionDto(item))
            .toList();
        
        resRoleDto.setPermissions(new HashSet<ResPermissionDto>(resPermissionDto));
        return resRoleDto;
    }


    
}
