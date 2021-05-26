package com.challenge.coding.github.model;

import java.time.LocalDateTime;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.Data;

@Data
@RedisHash("GitHub")
public class GitHub {
	private String id;
	private Integer statusCode;
	private String response;
	private LocalDateTime creationDateTime;
	@TimeToLive
	private long expiration = 60L;
}