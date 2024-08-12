package com.commerce.userapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.commercedomain.common.UserVo;
import com.commerce.commercedomain.config.JwtAuthenticationProvider;
import com.commerce.userapi.domain.customer.ChangeBalanceForm;
import com.commerce.userapi.domain.customer.CustomerDto;
import com.commerce.userapi.domain.model.Customer;
import com.commerce.userapi.exception.CustomException;
import com.commerce.userapi.exception.ErrorCode;
import com.commerce.userapi.service.customer.CustomerBalanceService;
import com.commerce.userapi.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

	private final JwtAuthenticationProvider jwtAuthenticationProvider;
	private final CustomerService customerService;
	private final CustomerBalanceService customerBalanceService;

	@GetMapping("/getInfo")
	public ResponseEntity<CustomerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
		UserVo userVo = jwtAuthenticationProvider.getUserVo(token);
		Customer customer = customerService.findByIdAndEmail(userVo.getId(), userVo.getEmail())
			.orElseThrow(
				() -> new CustomException(ErrorCode.USER_NOT_FOUND)
			);
		return ResponseEntity.ok(CustomerDto.from(customer));
	}

	@PostMapping("/balance")
	public ResponseEntity<Integer> changeBalance(@RequestHeader(name = "X-AUTH-TOKEN") String token,
		@RequestBody ChangeBalanceForm changeBalanceForm) {

		UserVo userVo = jwtAuthenticationProvider.getUserVo(token);
		return ResponseEntity.ok(
			customerBalanceService.changeBalance(userVo.getId(), changeBalanceForm).getCurrentMoney());
	}
}
