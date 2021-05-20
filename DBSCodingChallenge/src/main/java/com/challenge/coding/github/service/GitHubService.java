package com.challenge.coding.github.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.challenge.coding.github.entity.GitHub;
import com.challenge.coding.github.repository.GitHubRepository;

@Service
public class GitHubService {

	@Bean
	private RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private GitHubRepository gitHubRepository;

	public GitHub getRepository(String owner, String repositoryName) {
		GitHub gitHub = gitHubRepository.findByOwnerAndRepositoryName(owner, repositoryName);

		if (gitHub == null) {
			gitHub = new GitHub();

			try {
				RequestEntity<Void> requestEntity = RequestEntity.get("https://api.github.com/repos/{owner}/{repo}", owner, repositoryName)
						.header("accept", "application/vnd.github.v3+json")
						.build();

				ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Map<String, Object>>() {});

				if (HttpStatus.OK == responseEntity.getStatusCode()) {
					Map<String, String> keyMapping = new HashMap<String, String>();
					keyMapping.put("full_name", "fullName");
					keyMapping.put("description", "description");
					keyMapping.put("clone_url", "cloneUrl");
					keyMapping.put("stargazers_count", "stars");
					keyMapping.put("created_at", "createdAt");

					Map<String, Object> responseBody = responseEntity.getBody().entrySet()
							.stream()
							.filter(map -> keyMapping.containsKey(map.getKey()))
							.collect(HashMap::new, (m, map) -> m.put(keyMapping.get(map.getKey()), map.getValue()), Map::putAll);

					gitHub.setOwner(owner);
					gitHub.setRepositoryName(repositoryName);
					gitHub.setResponse(responseBody.toString());
					gitHub.setCreationDateTime(LocalDateTime.now());

					gitHubRepository.save(gitHub);
				}
			}
			catch (HttpStatusCodeException e) {
				String response = "Unhandled status code";

				if (HttpStatus.MOVED_PERMANENTLY == e.getStatusCode()) {
					response = HttpStatus.MOVED_PERMANENTLY.toString();
				}
				else if (HttpStatus.FORBIDDEN == e.getStatusCode()) {
					response = HttpStatus.FORBIDDEN.toString();
				}
				else if (HttpStatus.NOT_FOUND == e.getStatusCode()) {
					response = HttpStatus.NOT_FOUND.toString();
				}

				gitHub.setResponse(response);
			}
			catch (Exception e) {
				gitHub.setResponse("Unable to call GitHub API");
			}
		}

		return gitHub;
	}

}