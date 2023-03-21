package com.example.springbootstudy.config;

import com.example.springbootstudy.interceptor.Logininterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new Logininterceptor()).addPathPatterns("/user/**")
                .excludePathPatterns("/swagger-ui/**");
    }
}
