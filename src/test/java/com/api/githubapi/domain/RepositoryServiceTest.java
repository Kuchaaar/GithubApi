package com.api.githubapi.domain;

import com.api.githubapi.facade.RepositoriesFacade;
import com.api.githubapi.models.branch.Branch;
import com.api.githubapi.models.branch.LastCommit;
import com.api.githubapi.models.repository.Owner;
import com.api.githubapi.models.repository.Repository;
import com.api.githubapi.models.response.UserRepositoriesResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class RepositoryServiceTest{
    @Mock
    private RepositoriesFacade repositoriesFacade;

    @InjectMocks
    private RepositoryService repositoryService;
    @Test
    void getAllRepositoriesAndBranchesForUser() {
        String username = "testUser";
        when(repositoriesFacade.getAllRepositoriesForUsername(username)).thenReturn(mockRepositories());
        when(repositoriesFacade.getAllBranchesForRepository(username, mockRepositories().getFirst().name())).thenReturn(mockBranches());
        List<UserRepositoriesResponse> result = repositoryService.getAllRepositoriesAndBranchesForUser(username);
        assertEquals(1, result.size());
        assertEquals("Repo1", result.getFirst().name());
        assertEquals("user1", result.getFirst().login());
        assertEquals(1, result.getFirst().branches().size());
        assertEquals("Branch1", result.getFirst().branches().getFirst().name());
        assertEquals("sha1",result.getFirst().branches().getFirst().commit().sha());
    }
    private List<Repository> mockRepositories(){
        return List.of(new Repository("Repo1", new Owner("user1"),false));
    }
    private List<Branch> mockBranches(){
        return List.of(new Branch("Branch1",new LastCommit("sha1")));
    }
}
