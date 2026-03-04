package com.example.auth_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // מאפשר גישה לכל ה-API
                        .allowedOrigins("http://localhost", "http://127.0.0.1", "http://localhost:4200") // הכתובות המותרות
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // הפעולות המותרות
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}