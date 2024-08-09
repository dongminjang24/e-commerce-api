package io.security.userapi.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.StringDecoder;

@Configuration
public class FeignConfig {

	@Value("${mailgun.key}")
	private String mailgunKey;

	@Qualifier("mailgun")
	@Bean
	public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
		return new BasicAuthRequestInterceptor("api", mailgunKey);
	}
	@Bean
	public Decoder feignDecoder() {
		return new ResponseEntityDecoder(new StringDecoder());
	}

}
