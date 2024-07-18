package com.example.sales_manager.service;

import com.example.sales_manager.dto.ResultPagination;
import com.example.sales_manager.dto.request.ReqRoleDto;
import com.example.sales_manager.entity.Role;
import com.example.sales_manager.exception.DataNotFoundException;
import com.example.sales_manager.repository.RoleRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final EntityManager entityManager;

    public RoleService(RoleRepository roleRepository, EntityManager entityManager) {
        this.roleRepository = roleRepository;
        this.entityManager = entityManager;
    }

    public Role handleGetRoleById(Long id) throws Exception {
        Role role = roleRepository.findById(id).orElseThrow(null);
        if (role == null) {
            throw new DataNotFoundException("Role not found");
        }
        return role;
    }

    public List<Role> handleGetRoles(Pageable pageable) throws Exception {
        Page<Role> page = roleRepository.findAll(pageable).orElseThrow(() -> new DataNotFoundException("No roles found"));
       
        List<Role> roles = page.getContent().stream().()

        ResultPagination resultPagination = new ResultPagination();
        ResultPagination.Meta meta = resultPagination.new Meta();

        meta.setPage(page.getNumber());
        meta.setPageSize(page.getSize());
        meta.setTotalPages(page.getTotalPages());
        meta.setTotalElements(page.getTotalElements());
        
        return 
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


    
}
