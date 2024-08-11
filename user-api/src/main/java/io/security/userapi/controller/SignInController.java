package io.security.userapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.security.userapi.application.SignInApplication;
import io.security.userapi.domain.SignInForm;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/singIn")
public class SignInController {

	private final SignInApplication signInApplication;

	@PostMapping
	public ResponseEntity<String> signInCustomer(@RequestBody SignInForm form) {
		return ResponseEntity.ok(signInApplication.customerLoginToken(form));
	}
}
