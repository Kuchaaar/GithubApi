package com.api.githubapi.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor githubFeignInterceptor() {
        return new GitHubFeignInterceptor();
    }
}
