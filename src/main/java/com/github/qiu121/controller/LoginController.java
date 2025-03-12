package com.github.qiu121.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.qiu121.common.Result;
import com.github.qiu121.dto.LoginDTO;
import com.github.qiu121.entity.Users;
import com.github.qiu121.service.IUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author to_Geek
 * @version 1.0
 * @date 2025/03/04
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class LoginController {

    private final IUsersService userService;
    private final StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginDTO user) {
        String username = user.getUsername().trim();
        String password = user.getPassword().trim();
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getUsername, username)
                .eq(Users::getPassword, password);
        Users one = userService.getOne(wrapper);

        Users one1 = userService.getOne(new LambdaQueryWrapper<Users>()
                .eq(Users::getUsername, username));
        if (one1 == null) {
            return Result.fail("用户名不存在");

        }
        if (one == null) {
            return Result.fail("账号或密码错误");
        }

        // 登录成功
        String token = StpUtil.getTokenValue();
        StpUtil.login(one.getUserId());
        stringRedisTemplate.opsForValue().set("token:" + token, one.getUsername(),7, TimeUnit.DAYS);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", StpUtil.getTokenValue());
        return Result.success(map);

    }

    @GetMapping("/logout")
    public Result<?> logout() {
        StpUtil.logout();
        return Result.success();
    }

}
