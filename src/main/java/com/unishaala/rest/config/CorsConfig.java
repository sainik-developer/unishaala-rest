package com.unishaala.rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("stage")
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders(HttpMethod.GET.name(),HttpMethod.POST.name(),HttpMethod.PUT.name(),HttpMethod.DELETE.name(),HttpMethod.PATCH.name())
                .allowedOrigins("http://localhost:3000")
                .allowCredentials(true)
                .allowedHeaders("Authorization","Accept","Content-Type","cache-control")
                .exposedHeaders("content-type","content-length");
    }
}
