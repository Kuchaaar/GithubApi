package com.api.GithubApi.domain;

import com.api.GithubApi.integration.BranchClient;
import com.api.GithubApi.integration.RepositoryClient;
import com.api.GithubApi.models.branch.Branch;
import com.api.GithubApi.models.repository.Owner;
import com.api.GithubApi.models.repository.Repository;
import com.api.GithubApi.models.response.ApiResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RepositoryService{
    private final RepositoryClient repositoryClient;
    private final BranchClient branchClient;

    public RepositoryService(RepositoryClient repositoryClient, BranchClient branchClient){
        this.repositoryClient = repositoryClient;
        this.branchClient = branchClient;
    }
    public List<ApiResponse> getAllRepositoriesAndBranches(String username){
        List<ApiResponse> apiResponses = new ArrayList<>();
        List<Repository> repositoryResponses =
                repositoryClient.getAllRepositories(username)
                        .stream()
                        .filter(repo -> !repo.fork())
                        .toList();
        Map<Repository,List<Branch>> repositoryBranchMap = new HashMap<>();
        for(Repository repository : repositoryResponses){
            List<Branch> branches = getAllBranchesForRepository(repository, username);
            repositoryBranchMap.put(repository, branches);
        }
        for(Map.Entry<Repository, List<Branch>> entry : repositoryBranchMap.entrySet()){
            Repository repository = entry.getKey();
            Owner owner = repository.owner();
            List<Branch> branchList = entry.getValue();
            apiResponses.add(mockToApiResponse(repository,owner,branchList));
        }
        return apiResponses;
    }
    private List<Branch> getAllBranchesForRepository(Repository repository,String username){
        return branchClient.getAllBranches(username, repository.name());
    }
    private ApiResponse mockToApiResponse(Repository repository, Owner owner, List<Branch> branch){
        return new ApiResponse(repository.name(),owner.login(),branch);
    }
}
