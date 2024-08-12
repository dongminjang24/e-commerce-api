package com.commerce.userapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.commerce.userapi.domain.SignInForm;
import com.commerce.userapi.application.SignInApplication;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/singIn")
public class SignInController {

	private final SignInApplication signInApplication;

	@PostMapping("/customer")
	public ResponseEntity<String> signInCustomer(@RequestBody SignInForm form) {
		return ResponseEntity.ok(signInApplication.customerLoginToken(form));
	}

	@PostMapping("/seller")
	public ResponseEntity<String> signInSeller(@RequestBody SignInForm form) {
		return ResponseEntity.ok(signInApplication.sellerLoginToken(form));
	}
}
