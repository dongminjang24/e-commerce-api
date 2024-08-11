package io.security.userapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.security.commercedomain.common.UserVo;
import io.security.commercedomain.config.JwtAuthenticationProvider;
import io.security.userapi.domain.customer.ChangeBalanceForm;
import io.security.userapi.domain.customer.CustomerDto;
import io.security.userapi.domain.model.Customer;
import io.security.userapi.exception.CustomException;
import io.security.userapi.exception.ErrorCode;
import io.security.userapi.service.customer.CustomerBalanceService;
import io.security.userapi.service.customer.CustomerService;
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
