package com.commerce.userapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.commerce.commercedomain.config.JwtAuthenticationProvider;

@Configuration
public class JwtConfig {

	@Bean
	public JwtAuthenticationProvider jwtAuthenticationProvider() {
		return new JwtAuthenticationProvider();
	}
}
