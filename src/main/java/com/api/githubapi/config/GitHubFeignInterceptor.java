package com.api.githubapi.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;


public class GitHubFeignInterceptor implements RequestInterceptor{
    @Value("${github.api.token}")
    private String githubApiToken;
    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", "Bearer " + githubApiToken);
    }
}
