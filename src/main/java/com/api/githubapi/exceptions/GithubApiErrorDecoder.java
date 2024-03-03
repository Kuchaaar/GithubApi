package com.api.githubapi.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;

public class GithubApiErrorDecoder implements ErrorDecoder{

    private final ObjectMapper mapper;

    public GithubApiErrorDecoder(ObjectMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        final GithubErrorResponse githubErrorResponse;
        try (InputStream bodyIs = response.body()
                .asInputStream()) {
            githubErrorResponse = mapper.readValue(bodyIs, GithubErrorResponse.class);
        } catch (IOException e) {
            return new CustomHttpException("Error while parsing response: %s".formatted(e.getMessage()), 500);
        }
        return switch (response.status()) {
            case 400 -> new CustomHttpException(githubErrorResponse != null ? githubErrorResponse.message() : "Bad Request", 400);
            case 404 -> new CustomHttpException(githubErrorResponse != null ? githubErrorResponse.message() : "Not found", 404);
            default -> new CustomHttpException(githubErrorResponse != null ? githubErrorResponse.message() : "Unexpected external error %s".formatted(response.status()), 502);
        };
    }
}