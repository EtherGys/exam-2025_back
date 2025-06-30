package com.bar_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Autowired
    private JwtAuthenticationInterceptor jwtAuthenticationInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthenticationInterceptor)
                .addPathPatterns("/api/**") // Appliquer à tous les endpoints API
                .excludePathPatterns(
                    "/api/clients/login",
                    "/api/clients/validate-token",
                    "/api/clients", // exclure POST /api/clients
                    "/api/test/**",
                    "/health",
                    "/info",
                    "/"
                ); // Exclure les endpoints publics
    }
} 