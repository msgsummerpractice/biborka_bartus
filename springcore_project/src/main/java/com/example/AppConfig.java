package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

@Configuration
@ComponentScan(basePackages = "java.com.example")    
public class AppConfig {

    @Bean
    public HelloSpring helloSpring() {
        return new HelloSpring();
    }

}
