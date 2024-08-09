package io.security.userapi.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.security.userapi.config.FeignConfig;

@SpringBootTest(classes = FeignConfig.class)
class EmailSendServiceTest {

	@Autowired
	private EmailSendService emailSendService;

	@Test
	public void emailTest() {
		emailSendService.sendEmail();
	}

}