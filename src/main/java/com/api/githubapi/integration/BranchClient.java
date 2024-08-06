package com.api.githubapi.integration;

import com.api.githubapi.config.RepositoryClientConfiguration;
import com.api.githubapi.models.branch.Branch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;

@ReactiveFeignClient(name = "branch",configuration = RepositoryClientConfiguration.class)
public interface BranchClient{
    @GetMapping(value = "{username}/{repositoryName}/branches")
    Flux<Branch> getAllBranches(@PathVariable("username") String username,
                                @PathVariable("repositoryName") String repositoryName,
                                @RequestHeader("Authorization") String authorizationToken);
}
