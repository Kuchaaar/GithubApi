package com.api.githubapi.models.response;

import com.api.githubapi.models.branch.Branch;

import java.util.List;

public record ApiResponse(String name, String login, List<Branch> branches){
}
