package com.challenge.coding.github;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.challenge.coding.github.model.GitHub;
import com.challenge.coding.github.model.GitHubRepositoryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GitHubService {
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private GitHubRepository gitHubRepository;

	public GitHub getRepository(String owner, String repositoryName) {
		GitHub gitHubEntity = gitHubRepository.findById(owner + "|" + repositoryName).orElse(null);

		if (gitHubEntity == null) {
			gitHubEntity = new GitHub();
			gitHubEntity.setId(owner + "|" + repositoryName);
			gitHubEntity.setCreationDateTime(LocalDateTime.now());

			try {
				RequestEntity<Void> requestEntity = RequestEntity.get("https://api.github.com/repos/{owner}/{repo}", owner, repositoryName)
						.header("accept", "application/vnd.github.v3+json")
						.build();

				ResponseEntity<GitHubRepositoryResponse> responseEntity = restTemplate.exchange(requestEntity, GitHubRepositoryResponse.class);

				if (HttpStatus.OK == responseEntity.getStatusCode()) {
					String response = null;

					try {
						response = objectMapper.writeValueAsString(responseEntity.getBody());
					}
					catch (JsonProcessingException e) {
						log.error("Error serialiazing response body as a string.", e);
					}

					gitHubEntity.setStatusCode(responseEntity.getStatusCode().value());
					gitHubEntity.setResponse(response);
				}
			}
			catch (HttpStatusCodeException e) {
				gitHubEntity.setStatusCode(e.getStatusCode().value());
			}
			catch (Exception e) {
				gitHubEntity.setStatusCode(HttpStatus.NOT_FOUND.value());
			}

			gitHubRepository.save(gitHubEntity);
		}

		return gitHubEntity;
	}
}