package com.challenge.coding.github.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.challenge.coding.github.entity.GitHub;

@Repository
public interface GitHubRepository extends CrudRepository<GitHub, Long> {

	public GitHub findByOwnerAndRepositoryName(String owner, String repositoryName);

}