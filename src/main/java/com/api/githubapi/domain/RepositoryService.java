package com.api.githubapi.domain;

import com.api.githubapi.config.GithubApiConfig;
import com.api.githubapi.exceptions.CustomHttpException;
import com.api.githubapi.models.branch.Branch;
import com.api.githubapi.models.repository.Repository;
import com.api.githubapi.models.response.BranchResponse;
import com.api.githubapi.models.response.UserRepositoriesResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RepositoryService{
    private final WebClient webClient;
    private final GithubApiConfig githubApiConfig;

    public RepositoryService(WebClient webClient, GithubApiConfig githubApiConfig){
        this.webClient = webClient;
        this.githubApiConfig = githubApiConfig;
    }


    public Flux<UserRepositoriesResponse> getAllRepositoriesWithBranches(String username){
        return webClient.get()
                .uri("/users/{username}/repos", username)
                .header("Authorization", "Bearer " + githubApiConfig.token())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new CustomHttpException("User not found", 404)))
                .bodyToFlux(Repository.class)
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
        return webClient.get()
                .uri("/repos/{username}/{repositoryName}/branches", username, repositoryName)
                .header("Authorization", "Bearer " + githubApiConfig.token())
                .retrieve()
                .bodyToFlux(Branch.class)
                .map(branch -> new BranchResponse(branch.name(), branch.commit().sha()));
    }
}