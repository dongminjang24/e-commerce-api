package com.commerce.userapi.application;

import org.springframework.stereotype.Service;

import com.commerce.commercedomain.common.UserType;
import com.commerce.commercedomain.config.JwtAuthenticationProvider;
import com.commerce.userapi.domain.SignInForm;
import com.commerce.userapi.domain.model.Customer;
import com.commerce.userapi.exception.CustomException;
import com.commerce.userapi.exception.ErrorCode;
import com.commerce.userapi.service.customer.CustomerService;
import com.commerce.userapi.domain.model.Seller;
import com.commerce.userapi.service.seller.SellerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignInApplication {

	private final CustomerService customerService;
	private final SellerService sellerService;
	private final JwtAuthenticationProvider jwtAuthenticationProvider;

	public String customerLoginToken(SignInForm form) {
		Customer customer = customerService.findValidCustomer(form.getEmail(), form.getPassword())
			.orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));


		return jwtAuthenticationProvider.createToken(form.getEmail(),customer.getId(), UserType.CUSTOMER);
	}

	public String sellerLoginToken(SignInForm form) {
		Seller seller = sellerService.findValidSeller(form.getEmail(), form.getPassword())
			.orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));


		return jwtAuthenticationProvider.createToken(form.getEmail(),seller.getId(), UserType.CUSTOMER);
	}
}
