package com.api.githubapi.facade;

import com.api.githubapi.config.GithubApiConfig;
import com.api.githubapi.integration.BranchClient;
import com.api.githubapi.integration.RepositoryClient;
import com.api.githubapi.models.branch.Branch;
import com.api.githubapi.models.repository.Repository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RepositoriesFacade{
    private final BranchClient branchClient;
    private final RepositoryClient repositoryClient;
    private final GithubApiConfig githubApiConfig;

    public RepositoriesFacade(BranchClient branchClient, RepositoryClient repositoryClient,
                              GithubApiConfig githubApiConfig){
        this.branchClient = branchClient;
        this.repositoryClient = repositoryClient;
        this.githubApiConfig = githubApiConfig;
    }

    public List<Branch> getAllBranchesForRepository(String username, String repositoryName){
        return branchClient.getAllBranches(username, repositoryName, "Bearer " + githubApiConfig.token());
    }

    public List<Repository> getAllRepositoriesForUsername(String username){
        return repositoryClient.getAllRepositories(username, "Bearer " + githubApiConfig.token());
    }
}
