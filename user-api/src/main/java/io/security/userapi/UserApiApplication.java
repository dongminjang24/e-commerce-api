package io.security.userapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.security.userapi.service.EmailSendService;
import lombok.RequiredArgsConstructor;

@EnableFeignClients
@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
public class UserApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApiApplication.class, args);

	}

}
