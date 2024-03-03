package com.api.githubapi.config;

import com.api.githubapi.exceptions.GithubApiErrorDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class RepositoryClientConfiguration{
    @Bean
    ErrorDecoder errorDecoder(ObjectMapper objectMapper){
        return new GithubApiErrorDecoder(objectMapper);
    }
}