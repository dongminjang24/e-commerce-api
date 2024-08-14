package com.commerce.orderapi.client;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.commerce.orderapi.domain.redis.Cart;
import com.commerce.orderapi.exception.CustomException;
import com.commerce.orderapi.exception.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisClient {
	private final RedisTemplate<String, String> redisTemplate;
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public <T> T get(Long key,Class<T> classType) {
		return get(key.toString(), classType);
	}
	public <T> T get(String key,Class<T> classType) {
		String value = redisTemplate.opsForValue().get(key);
		if(value == null) {
			return null;
		}
		try {
			return objectMapper.readValue(value, classType);
		} catch (JsonProcessingException e) {
			log.error("Failed to parsing value from redis", e);
			return null;
		}
	}

	public void put(Long key, Cart cart) {
		put(key.toString(),cart);
	}

	private void put(String key, Cart cart) {
		try {
			redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(cart));
		} catch (JsonProcessingException e) {
			throw new CustomException(ErrorCode.NOT_FOUND_PRODUCT);
		}
	}

}
