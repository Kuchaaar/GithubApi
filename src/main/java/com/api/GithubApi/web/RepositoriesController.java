package com.api.GithubApi.web;

import com.api.GithubApi.domain.RepositoryService;
import com.api.GithubApi.models.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api")
public class RepositoriesController{
    private final RepositoryService repositoryService;

    public RepositoriesController(RepositoryService repositoryService){
        this.repositoryService = repositoryService;
    }
    @GetMapping("/repositories")
    public List<ApiResponse> getAllRepositoriesAndBranches(@RequestParam String username){
        return repositoryService.getAllRepositoriesAndBranches(username);
    }
}
