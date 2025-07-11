package com.hwadee.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class MyConfig implements WebMvcConfigurer {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "/login/**","/user/**" };

    //添加拦截器配置
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        registry.addInterceptor(new MyInterceptor())
                //添加拦截路径
                .addPathPatterns("/**")
                //添加放行路径
                .excludePathPatterns(CLASSPATH_RESOURCE_LOCATIONS);
    }

}
