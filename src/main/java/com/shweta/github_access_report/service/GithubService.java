package com.shweta.github_access_report.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GithubService {

    @Value("${github.api.url}")
    private String apiUrl;

    @Value("${github.token}")
    private String token;

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, List<String>> getAccessReport(String organization) {

        Map<String, List<String>> accessReport = new ConcurrentHashMap<>();

        List<Map<String, Object>> repositories = getRepositories(organization);

        if (repositories == null) return accessReport;

        // Parallel processing for scalability
        repositories.parallelStream().forEach(repo -> {

            String repoName = (String) repo.get("name");

            try {

                List<Map<String, Object>> collaborators =
                        getCollaborators(organization, repoName);

                if (collaborators == null) return;

                for (Map<String, Object> user : collaborators) {

                    String username = (String) user.get("login");

                    accessReport
                            .computeIfAbsent(username, k -> new ArrayList<>())
                            .add(repoName);
                }

            } catch (Exception ignored) {}

        });

        return accessReport;
    }

    private List<Map<String, Object>> getRepositories(String organization) {

        String url = apiUrl + "/orgs/" + organization + "/repos?per_page=100";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, List.class);

        return response.getBody();
    }

    private List<Map<String, Object>> getCollaborators(String organization, String repoName) {

        String url = apiUrl + "/repos/" + organization + "/" + repoName + "/collaborators";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, List.class);

        return response.getBody();
    }
}