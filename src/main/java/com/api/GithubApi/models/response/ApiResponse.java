package com.api.GithubApi.models.response;

import com.api.GithubApi.models.branch.Branch;

import java.util.List;

public record ApiResponse(String name, String login, List<Branch> branches){
}
