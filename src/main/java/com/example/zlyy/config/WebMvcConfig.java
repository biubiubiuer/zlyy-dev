package com.example.zlyy.config;

import com.example.zlyy.handler.AdminHandler;
import com.example.zlyy.handler.LoginHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private LoginHandler loginHandler;
    
    @Resource
    private AdminHandler adminHandler;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginHandler);
        registry.addInterceptor(adminHandler);
    }
}