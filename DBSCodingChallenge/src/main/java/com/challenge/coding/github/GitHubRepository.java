package com.challenge.coding.github;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.challenge.coding.github.model.GitHub;

@Repository
public interface GitHubRepository extends CrudRepository<GitHub, String> {
}