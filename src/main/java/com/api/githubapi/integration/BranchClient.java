package com.api.githubapi.integration;

import com.api.githubapi.models.branch.Branch;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "branch")
public interface BranchClient{
    @GetMapping(value = "{username}/{repositoryName}/branches")
    List<Branch> getAllBranches(@PathVariable("username") String username,
                                @PathVariable("repositoryName") String repositoryName,
                                @RequestHeader("Authorization") String header);
}
