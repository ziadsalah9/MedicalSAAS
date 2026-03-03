package com.MidecalApp.VideoConsultatntAppDemo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webConfigs {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // هيطبق على كل الأندبوينتس اللي بتبدأ بـ api
                        .allowedOrigins("http://127.0.0.1:5500", "http://localhost:5500") // حدد بورت الفرونت إند بتاعك
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // العمليات المسموحة
                        .allowedHeaders("*") // السماح بكل الـ Headers
                        .allowCredentials(true); // مهم لو فيه Cookies أو Sessions
            }
        };
    }

}
