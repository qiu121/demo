package com.github.qiu121.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.qiu121.common.Result;
import com.github.qiu121.entity.Permission;
import com.github.qiu121.entity.Role;
import com.github.qiu121.entity.Users;
import com.github.qiu121.service.IUsersService;
import com.github.qiu121.util.UserUtil;
import com.github.qiu121.vo.UserInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author to_Geek
 * @version 1.0
 * @date 2025/03/09
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final IUsersService userService;
    private final UserUtil userUtil;

    @GetMapping("/getUserInfo")
    public Result<UserInfoVO> getUserInfo() {
        Object userId = StpUtil.getLoginId();
        Users user = userService.getOne(new LambdaQueryWrapper<Users>()
                .eq(Users::getUserId, userId));

        Role role = userUtil.getRole(userId);
        List<Permission> permissionList = userUtil.getPermission(userId);
        UserInfoVO userInfoVO = UserInfoVO.builder().username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(role)
                .permission(permissionList)
                .build();
        return Result.success(userInfoVO);
    }

}
