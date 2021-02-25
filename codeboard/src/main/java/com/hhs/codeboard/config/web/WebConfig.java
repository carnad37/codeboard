package com.hhs.codeboard.config.web;

import org.springframework.context.annotation.Configuration;
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
    
}