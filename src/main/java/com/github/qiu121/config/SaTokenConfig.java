package com.github.qiu121.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Leo
 * @version 1.0
 * @date 2025/03/10
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handle -> {
            SaRouter.match("/**")
                    .notMatch("/api/users/login")
                    .check(r -> StpUtil.checkLogin());

        }));
        // SaRouter.match("/admin", r -> StpUtil.checkRole("admin"));
        // SaRouter.match("/user", r -> StpUtil.checkRole("user"));
    }
}
