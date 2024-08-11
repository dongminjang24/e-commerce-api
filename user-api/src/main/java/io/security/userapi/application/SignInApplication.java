package io.security.userapi.application;

import org.springframework.stereotype.Service;

import io.security.commercedomain.common.UserType;
import io.security.commercedomain.config.JwtAuthenticationProvider;
import io.security.userapi.domain.SignInForm;
import io.security.userapi.domain.model.Customer;
import io.security.userapi.exception.CustomException;
import io.security.userapi.exception.ErrorCode;
import io.security.userapi.service.CustomerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignInApplication {

	private final CustomerService customerService;
	private final JwtAuthenticationProvider jwtAuthenticationProvider;

	public String customerLoginToken(SignInForm form) {
		Customer customer = customerService.findValidCustomer(form.getEmail(), form.getPassword())
			.orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));


		return jwtAuthenticationProvider.createToken(form.getEmail(),customer.getId(), UserType.CUSTOMER);
	}
}
