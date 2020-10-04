package com.unishaala.rest.config;

import com.unishaala.rest.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializeDataConfig {
    @Bean
    public ApplicationRunner initializer(UserRepository repository) {
        return arg -> {
            // add three admin,
            // add five schools
            // add 2,3,4,5,6 classes for each school
            // add 20 students distributed among all class
            // add two course
            // add five sessions
        };
    }
}
