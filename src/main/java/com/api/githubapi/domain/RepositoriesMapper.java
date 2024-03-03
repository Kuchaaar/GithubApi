package com.api.githubapi.domain;

import com.api.githubapi.models.branch.Branch;
import com.api.githubapi.models.repository.Owner;
import com.api.githubapi.models.repository.Repository;
import com.api.githubapi.models.response.BranchResponse;
import com.api.githubapi.models.response.UserRepositoriesResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RepositoriesMapper{
    public UserRepositoriesResponse mapToUserRepositoriesResponse(Repository repository,
                                                                  Owner owner,
                                                                  List<Branch> branches){
        return new UserRepositoriesResponse(repository.name(), owner.login(), mapToBranchResponses(branches));
    }

    private List<BranchResponse> mapToBranchResponses(List<Branch> branches){
        return branches.stream()
                .map(branch -> new BranchResponse(branch.name(), branch.commit().sha()))
                .toList();
    }
}