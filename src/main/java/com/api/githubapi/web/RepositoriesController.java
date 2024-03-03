package com.api.githubapi.web;

import com.api.githubapi.domain.RepositoryService;
import com.api.githubapi.models.response.UserRepositoriesResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RepositoriesController{
    private final RepositoryService repositoryService;

    public RepositoriesController(RepositoryService repositoryService){
        this.repositoryService = repositoryService;
    }
    @GetMapping("/repositories")
    public List<UserRepositoriesResponse> getAllRepositoriesAndBranches(@RequestParam String username){
        return repositoryService.getAllRepositoriesAndBranchesForUser(username);
    }
}
