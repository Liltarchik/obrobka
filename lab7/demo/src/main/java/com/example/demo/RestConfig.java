package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(Movie.class);
        
        // Також додамо налаштування CORS, оскільки frontend буде звертатися до API
        cors.addMapping("/api/**")
            .allowedOrigins("http://localhost:5173", "http://localhost:3000", "http://127.0.0.1:5173", "*") // або вкажіть конкретні домени
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}
