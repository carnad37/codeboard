package com.hhs.codeboard.config.web;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import com.hhs.codeboard.config.anno.AspectMenuActive;
import com.hhs.codeboard.web.handler.SiteInterceptorHandler;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/dist/**")
            .addResourceLocations("classpath:/dist/")
            .setCachePeriod(20);
        registry.addResourceHandler("/plugins/**")
            .addResourceLocations("classpath:/plugins/")
            .setCachePeriod(20);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converter.setWriteAcceptCharset(false);
        converters.add(converter);
        WebMvcConfigurer.super.configureMessageConverters(converters);
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> URL_PATTERNS = Arrays.asList("/main/**", "/board/**","/menu/**", "/category/**");
        registry.addInterceptor(handlerInterceptor())
        	.addPathPatterns(URL_PATTERNS)
            .excludePathPatterns("/open/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Bean
    public HandlerInterceptor handlerInterceptor() {
        return new SiteInterceptorHandler();
    }
}