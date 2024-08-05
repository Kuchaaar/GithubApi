package com.api.githubapi.domain;

import com.api.githubapi.models.response.UserRepositoriesResponse;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(classes = {RepositoryServiceTest.TestConfig.class})
@TestPropertySource(properties = {"github.api.token=dummy_token"})
class RepositoryServiceTest {

    @LocalServerPort
    private int port;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        configureFor("localhost", wireMockServer.getPort());
    }

    @Configuration
    static class TestConfig {
        @Bean
        public WebClient webClient() {
            return WebClient.builder().baseUrl("http://localhost:" + wireMockServer.getPort()).build();
        }
    }

    @Test
    void getAllRepositoriesWithBranches() {
        // Mock the /users/{username}/repos endpoint
        wireMockServer.stubFor(get(urlPathEqualTo("/users/testuser/repos"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                        .withBody("[{\"name\":\"repo1\",\"owner\":{\"login\":\"testuser\"},\"fork\":false}]")));

        // Mock the /repos/{username}/{repositoryName}/branches endpoint
        wireMockServer.stubFor(get(urlPathEqualTo("/repos/testuser/repo1/branches"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                        .withBody("[{\"name\":\"main\",\"commit\":{\"sha\":\"12345\"}}]")));

        webTestClient.get()
                .uri("/repositories/testuser")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserRepositoriesResponse.class)
                .value(userRepositoriesResponses -> {
                    UserRepositoriesResponse response = userRepositoriesResponses.getFirst();
                    assertEquals("repo1", response.name());
                    assertEquals("testuser", response.login());
                    assertEquals(1, response.branches().size());
                    assertEquals("main", response.branches().getFirst().branchName());
                    assertEquals("12345", response.branches().getFirst().lastCommitSha());
                });
    }
}