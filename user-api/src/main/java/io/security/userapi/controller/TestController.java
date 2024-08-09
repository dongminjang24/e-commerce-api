package io.security.userapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import feign.Response;
import io.security.userapi.service.EmailSendService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {

	private final EmailSendService emailSendService;

	@GetMapping
	public ResponseEntity<String>  sendTestMail() {
		return emailSendService.sendEmail();
	}

}
