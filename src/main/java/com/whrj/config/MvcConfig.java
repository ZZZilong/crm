package com.whrj.config;


import com.whrj.interceptor.NoLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public NoLoginInterceptor noLoginInterceptor() {
        return new NoLoginInterceptor();
    }

    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(noLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/index", "/user/login", "/css/**", "/images/**", "/js/**", "/lib/**");
    }
}
