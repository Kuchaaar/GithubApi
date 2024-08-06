package com.api.githubapi.integration;

import com.api.githubapi.config.RepositoryClientConfiguration;
import com.api.githubapi.models.repository.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;

@ReactiveFeignClient(name = "repository",configuration = RepositoryClientConfiguration.class)
public interface RepositoryClient{
    @GetMapping(value = "{username}/repos")
    Flux<Repository> getAllRepositories(@PathVariable("username") String username,
                                        @RequestHeader("Authorization") String authorizationToken);
}
