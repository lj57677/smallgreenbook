package com.psl.config;

import com.psl.Interceptor.LoginCheckInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    private LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册接口、登录接口不拦截
        registry.addInterceptor(loginCheckInterceptor).excludePathPatterns(
                "/user/login",
                "/user/register"
        );
    }


}
