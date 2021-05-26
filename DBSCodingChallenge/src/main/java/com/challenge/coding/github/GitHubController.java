package com.challenge.coding.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.coding.github.model.GitHub;
import com.challenge.coding.github.model.GitHubRepositoryResponse;
import com.challenge.coding.github.model.GitHubResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class GitHubController {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private GitHubService gitHubService;

	@RequestMapping(method = RequestMethod.GET, path = "/repositories/{owner}/{repositoryName}")
	public ResponseEntity<GitHubResponse> getRepository(@PathVariable String owner, @PathVariable String repositoryName) {
		GitHub gitHubEntity = gitHubService.getRepository(owner, repositoryName);

		ResponseEntity<GitHubResponse> responseEntity = null;

		if (HttpStatus.OK.value() == gitHubEntity.getStatusCode()) {
			try {
				GitHubRepositoryResponse gitHubRepositoryResponse = objectMapper.readValue(gitHubEntity.getResponse(), GitHubRepositoryResponse.class);

				GitHubResponse gitHubResponse = new GitHubResponse();
				gitHubResponse.setFullName(gitHubRepositoryResponse.getFullName());
				gitHubResponse.setDescription(gitHubRepositoryResponse.getDescription());
				gitHubResponse.setCloneUrl(gitHubRepositoryResponse.getCloneUrl());
				gitHubResponse.setStars(gitHubRepositoryResponse.getStars());
				gitHubResponse.setCreatedAt(gitHubRepositoryResponse.getCreatedAt());

				responseEntity = new ResponseEntity<GitHubResponse>(gitHubResponse, HttpStatus.OK);
			}
			catch (JsonProcessingException e) {
				log.error("Error deserializing response body", e);
			}
		}
		else if (HttpStatus.MOVED_PERMANENTLY.value() == gitHubEntity.getStatusCode()) {
			responseEntity = new ResponseEntity<GitHubResponse>((GitHubResponse) null, HttpStatus.MOVED_PERMANENTLY);
		}
		else if (HttpStatus.FORBIDDEN.value() == gitHubEntity.getStatusCode()) {
			responseEntity = new ResponseEntity<GitHubResponse>((GitHubResponse) null, HttpStatus.FORBIDDEN);
		}
		else {
			responseEntity = new ResponseEntity<GitHubResponse>((GitHubResponse) null, HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}
}