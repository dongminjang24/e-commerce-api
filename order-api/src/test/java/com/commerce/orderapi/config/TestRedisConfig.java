// package com.commerce.orderapi.config;
// import org.springframework.boot.test.context.TestConfiguration;
// import org.springframework.context.annotation.Bean;
// import redis.embedded.RedisServer;
//
// import jakarta.annotation.PostConstruct;
// import jakarta.annotation.PreDestroy;
//
// @TestConfiguration
// public class TestRedisConfig {  // 테스트용 redis
//
// 	private final RedisServer redisServer;
//
// 	public TestRedisConfig() {
// 		this.redisServer = new RedisServer(6379);
// 	}
//
// 	@Bean
// 	public RedisServer redisServer() {
// 		return redisServer;
// 	}
//
// 	@PostConstruct
// 	public void startRedis() {
// 		redisServer.start();
// 	}
//
// 	@PreDestroy
// 	public void stopRedis() {
// 		redisServer.stop();
// 	}
// }