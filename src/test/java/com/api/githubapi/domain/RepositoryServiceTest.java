package com.api.githubapi.domain;

import com.api.githubapi.facade.RepositoriesFacade;
import com.api.githubapi.models.branch.Branch;
import com.api.githubapi.models.branch.LastCommit;
import com.api.githubapi.models.repository.Owner;
import com.api.githubapi.models.repository.Repository;
import com.api.githubapi.models.response.BranchResponse;
import com.api.githubapi.models.response.UserRepositoriesResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RepositoryServiceTest{
    @MockBean
    private RepositoriesFacade repositoriesFacade;
    @Autowired
    private RepositoryService repositoryService;

    @Test
    void getAllRepositoriesAndBranchesForUser(){
        //given
        List<UserRepositoriesResponse> expected = mockUserRepositoriesResponse();
        String username = "testUser";
        when(repositoriesFacade.getAllRepositoriesForUsername(username)).thenReturn(mockRepositories());
        when(repositoriesFacade.getAllBranchesForRepository(username, mockRepositories().getFirst().name())).thenReturn(
                mockBranches());
        //when
        List<UserRepositoriesResponse> result = repositoryService.getAllRepositoriesAndBranchesForUser(username);
        //then
        assertEquals(expected, result);
    }

    private List<Repository> mockRepositories(){
        return List.of(new Repository("Repo1", new Owner("user1"), false));
    }

    private List<Branch> mockBranches(){
        return List.of(new Branch("Branch1", new LastCommit("sha1")));
    }

    private List<UserRepositoriesResponse> mockUserRepositoriesResponse(){
        return List.of(new UserRepositoriesResponse("Repo1", "user1", List.of(new BranchResponse("Branch1", "sha1"))));
    }
}