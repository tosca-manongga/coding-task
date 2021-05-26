package com.challenge.coding.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GitHubRepositoryResponse {
	@JsonProperty(value = "full_name")
	private String fullName;
	@JsonProperty(value = "description")
	private String description;
	@JsonProperty(value = "clone_url")
	private String cloneUrl;
	@JsonProperty(value = "stargazers_count")
	private int stars;
	@JsonProperty(value = "created_at")
	private String createdAt;
}