package com.api.githubapi.domain;

import com.api.githubapi.facade.RepositoriesFacade;
import com.api.githubapi.models.branch.Branch;
import com.api.githubapi.models.repository.Repository;
import com.api.githubapi.models.response.UserRepositoriesResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepositoryService{
    private final RepositoriesFacade repositoriesFacade;
    private final RepositoriesMapper repositoriesMapper;

    public RepositoryService(RepositoriesFacade repositoriesFacade, RepositoriesMapper repositoriesMapper){
        this.repositoriesFacade = repositoriesFacade;
        this.repositoriesMapper = repositoriesMapper;
    }

    public List<UserRepositoriesResponse> getAllRepositoriesAndBranchesForUser(String username){
        return repositoriesFacade.getAllRepositoriesForUsername(username)
                .parallelStream()
                .filter(repo -> ! repo.fork())
                .map(repository -> {
                    List<Branch> branches = getAllBranchesForRepository(repository, username);
                    return repositoriesMapper.mapToUserRepositoriesResponse(repository, repository.owner(), branches);
                })
                .toList();
    }

    private List<Branch> getAllBranchesForRepository(Repository repository, String username){
        return repositoriesFacade.getAllBranchesForRepository(username, repository.name());
    }
}