package com.challenge.coding.github.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.coding.github.entity.GitHub;
import com.challenge.coding.github.service.GitHubService;

@RestController
public class GitHubController {

	@Autowired
	private GitHubService gitHubService;

	@RequestMapping(method = RequestMethod.GET, path = "/repositories/{owner}/{repositoryName}")
	public String getRepository(@PathVariable String owner, @PathVariable String repositoryName) {
		GitHub gitHub = gitHubService.getRepository(owner, repositoryName);

		return gitHub.getResponse();
	}

}