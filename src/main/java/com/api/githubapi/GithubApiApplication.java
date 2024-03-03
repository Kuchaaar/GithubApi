package com.api.githubapi;

import com.api.githubapi.config.GithubApiConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(GithubApiConfig.class)
public class GithubApiApplication{

	public static void main(String[] args){
		SpringApplication.run(GithubApiApplication.class, args);
	}
}
