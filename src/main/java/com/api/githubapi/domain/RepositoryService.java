package com.api.githubapi.domain;

import com.api.githubapi.config.GithubApiConfig;
import com.api.githubapi.exceptions.CustomHttpException;
import com.api.githubapi.integration.BranchClient;
import com.api.githubapi.integration.RepositoryClient;
import com.api.githubapi.models.response.BranchResponse;
import com.api.githubapi.models.response.UserRepositoriesResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;

@Service
public class RepositoryService{
    private final GithubApiConfig githubApiConfig;
    private final BranchClient branchClient;
    private final RepositoryClient repositoryClient;

    public RepositoryService(GithubApiConfig githubApiConfig,
                             BranchClient branchClient,
                             RepositoryClient repositoryClient){
        this.githubApiConfig = githubApiConfig;
        this.branchClient = branchClient;
        this.repositoryClient = repositoryClient;
    }


    public Flux<UserRepositoriesResponse> getAllRepositoriesWithBranches(String username){
        return repositoryClient.getAllRepositories(username, githubApiConfig.token())
                .onErrorResume(e -> {
                    if(e instanceof WebClientResponseException webClientResponseException &&
                            ((WebClientResponseException) e).getStatusCode().is4xxClientError()){
                        return Flux.error(new CustomHttpException("User not found", 404));
                    }
                    return Flux.error(e);
                })
                .filter(repository -> ! repository.fork())
                .flatMap(repository ->
                        getBranchesForRepository(username, repository.name())
                                .collectList()
                                .map(branches -> new UserRepositoriesResponse(repository.name(),
                                        repository.owner().login(),
                                        branches))
                );
    }

    private Flux<BranchResponse> getBranchesForRepository(String username, String repositoryName){
        return branchClient.getAllBranches(username, repositoryName, githubApiConfig.token())
                .map(branch -> new BranchResponse(branch.name(), branch.commit().sha()));
    }
}