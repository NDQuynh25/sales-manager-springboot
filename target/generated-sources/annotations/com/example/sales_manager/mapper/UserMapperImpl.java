package com.example.sales_manager.mapper;

import com.example.sales_manager.dto.response.PermissionRes;
import com.example.sales_manager.dto.response.RoleRes;
import com.example.sales_manager.dto.response.UserRes;
import com.example.sales_manager.entity.Permission;
import com.example.sales_manager.entity.Role;
import com.example.sales_manager.entity.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-07T17:21:05+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserRes mapToUserRes(User user) {
        if ( user == null ) {
            return null;
        }

        UserRes.UserResBuilder userRes = UserRes.builder();

        userRes.cartId( mapToUserResWithCartId( user ) );
        userRes.address( user.getAddress() );
        userRes.avatar( user.getAvatar() );
        userRes.createdAt( user.getCreatedAt() );
        userRes.createdBy( user.getCreatedBy() );
        userRes.dateOfBirth( user.getDateOfBirth() );
        userRes.email( user.getEmail() );
        userRes.fullName( user.getFullName() );
        userRes.gender( user.getGender() );
        userRes.id( user.getId() );
        userRes.isActive( user.getIsActive() );
        userRes.phoneNumber( user.getPhoneNumber() );
        userRes.role( roleToRoleRes( user.getRole() ) );
        userRes.updatedAt( user.getUpdatedAt() );
        userRes.updatedBy( user.getUpdatedBy() );

        return userRes.build();
    }

    protected PermissionRes permissionToPermissionRes(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionRes permissionRes = new PermissionRes();

        permissionRes.setId( permission.getId() );
        permissionRes.setPermissionName( permission.getPermissionName() );
        permissionRes.setApiAccess( permission.getApiAccess() );
        permissionRes.setMethod( permission.getMethod() );
        permissionRes.setDescription( permission.getDescription() );
        permissionRes.setIsActive( permission.getIsActive() );
        permissionRes.setCreatedBy( permission.getCreatedBy() );
        permissionRes.setUpdatedBy( permission.getUpdatedBy() );
        permissionRes.setCreatedAt( permission.getCreatedAt() );
        permissionRes.setUpdatedAt( permission.getUpdatedAt() );

        return permissionRes;
    }

    protected Set<PermissionRes> permissionSetToPermissionResSet(Set<Permission> set) {
        if ( set == null ) {
            return null;
        }

        Set<PermissionRes> set1 = new LinkedHashSet<PermissionRes>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Permission permission : set ) {
            set1.add( permissionToPermissionRes( permission ) );
        }

        return set1;
    }

    protected RoleRes roleToRoleRes(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleRes roleRes = new RoleRes();

        roleRes.setId( role.getId() );
        roleRes.setRoleName( role.getRoleName() );
        roleRes.setIsActive( role.getIsActive() );
        roleRes.setPermissions( permissionSetToPermissionResSet( role.getPermissions() ) );
        roleRes.setCreatedBy( role.getCreatedBy() );
        roleRes.setUpdatedBy( role.getUpdatedBy() );
        roleRes.setCreatedAt( role.getCreatedAt() );
        roleRes.setUpdatedAt( role.getUpdatedAt() );

        return roleRes;
    }
}
