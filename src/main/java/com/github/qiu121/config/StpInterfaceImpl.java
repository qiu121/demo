package com.github.qiu121.config;

import cn.dev33.satoken.stp.StpInterface;
import com.github.qiu121.entity.Permission;
import com.github.qiu121.entity.Role;
import com.github.qiu121.util.UserUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Leo
 * @version 1.0
 * @date 2025/03/10
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    @Resource
    private UserUtil userUtil;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {

        List<Permission> permission = userUtil.getPermission(loginId);
        List<String> permissionList = permission.stream()
                .map(Permission::getPermissionCode)
                .toList();
        return permissionList;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {

        Role role = userUtil.getRole(loginId);
        return List.of(role.getRole());
    }
}
