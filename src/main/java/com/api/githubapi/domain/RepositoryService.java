package com.api.githubapi.domain;

import com.api.githubapi.integration.BranchClient;
import com.api.githubapi.integration.RepositoryClient;
import com.api.githubapi.models.branch.Branch;
import com.api.githubapi.models.repository.Owner;
import com.api.githubapi.models.repository.Repository;
import com.api.githubapi.models.response.ApiResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RepositoryService{
    private final RepositoryClient repositoryClient;
    private final BranchClient branchClient;

    public RepositoryService(RepositoryClient repositoryClient, BranchClient branchClient){
        this.repositoryClient = repositoryClient;
        this.branchClient = branchClient;
    }

    public List<ApiResponse> getAllRepositoriesAndBranches(String username){
        return repositoryClient.getAllRepositories(username)
                .stream()
                .filter(repo -> ! repo.fork())
                .map(repository -> {
                    List<Branch> branches = getAllBranchesForRepository(repository, username);
                    return mockToApiResponse(repository, repository.owner(), branches);
                })
                .collect(Collectors.toList());
    }

    private List<Branch> getAllBranchesForRepository(Repository repository, String username){
        return branchClient.getAllBranches(username, repository.name());
    }

    private ApiResponse mockToApiResponse(Repository repository, Owner owner, List<Branch> branch){
        return new ApiResponse(repository.name(), owner.login(), branch);
    }
}
