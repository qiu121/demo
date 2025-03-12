package com.github.qiu121.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.qiu121.entity.Permission;
import com.github.qiu121.entity.Role;
import com.github.qiu121.entity.RolePermission;
import com.github.qiu121.entity.UserRole;
import com.github.qiu121.mapper.PermissionMapper;
import com.github.qiu121.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Leo
 * @version 1.0
 * @date 2025/03/10
 */
@RequiredArgsConstructor
@Component
public class UserUtil {

    private final IUsersService userService;
    private final IRoleService roleService;
    private final IUserRoleService userRoleService;
    private final IRolePermissionService rolePermissionService;
    private final IPermissionService permissionService;
    private final PermissionMapper permissionMapper;

    public Role getRole(Object userId) {
        UserRole userRole = userRoleService.getOne(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userId));
        Long roleId = userRole.getRoleId();

        Role role = roleService.getOne(new LambdaQueryWrapper<Role>()
                .eq(Role::getRoleId, roleId));
        return role;
    }

    public List<Permission> getPermission(Object userId) {
        Role role = getRole(userId);
        List<RolePermission> rolePermissionList = rolePermissionService.list(new LambdaQueryWrapper<RolePermission>()
                .eq(RolePermission::getRoleId, role.getRoleId()));

        List<Permission> permissionList = rolePermissionList.stream()
                .map(s -> permissionService.getOne(new LambdaQueryWrapper<Permission>()
                        .eq(Permission::getPermissionId, s.getPermissionId())))
                .toList();
        System.out.println(permissionList);


        return permissionList;
    }

}
