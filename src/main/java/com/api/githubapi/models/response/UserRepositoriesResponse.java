package com.api.githubapi.models.response;

import java.util.List;

public record UserRepositoriesResponse(String name, String login, List<BranchResponse> branches){
}