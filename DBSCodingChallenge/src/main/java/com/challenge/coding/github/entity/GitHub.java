package com.challenge.coding.github.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GitHub {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String owner;

	@Column
	private String repositoryName;

	@Column(length = Integer.MAX_VALUE)
	private String response;

	@Column
	private LocalDateTime creationDateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(LocalDateTime creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	@Override
	public String toString() {
		return "GitHub [id=" + id + ", owner=" + owner + ", repositoryName=" + repositoryName + ", response=" + response + ", creationDateTime=" + creationDateTime + "]";
	}

}