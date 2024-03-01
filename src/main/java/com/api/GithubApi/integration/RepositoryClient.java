package com.api.GithubApi.integration;

import com.api.GithubApi.models.repository.Repository;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "repository")
public interface RepositoryClient{
    @GetMapping(value = "{username}/repos")
    List<Repository> getAllRepositories(@PathVariable("username") String username);
}
