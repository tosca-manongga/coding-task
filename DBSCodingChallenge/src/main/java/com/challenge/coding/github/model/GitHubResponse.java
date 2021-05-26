package com.challenge.coding.github.model;

import lombok.Data;

@Data
public class GitHubResponse {
	private String fullName;
	private String description;
	private String cloneUrl;
	private int stars;
	private String createdAt;
}