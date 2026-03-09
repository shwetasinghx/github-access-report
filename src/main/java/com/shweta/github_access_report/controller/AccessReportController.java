package com.shweta.github_access_report.controller;

import com.shweta.github_access_report.service.GithubService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccessReportController {

    private final GithubService githubService;

    public AccessReportController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/access-report/{organization}")
    public Map<String, List<String>> getAccessReport(@PathVariable String organization) {

        return githubService.getAccessReport(organization);

    }
}