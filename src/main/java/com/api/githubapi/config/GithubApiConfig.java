package com.api.githubapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "github.api")
public record GithubApiConfig(
        String token
){
}