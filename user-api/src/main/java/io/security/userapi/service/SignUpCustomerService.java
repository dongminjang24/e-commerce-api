package io.security.userapi.service;


import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.security.userapi.domain.SignUpForm;
import io.security.userapi.domain.model.Customer;
import io.security.userapi.domain.repository.CustomerRepository;
import io.security.userapi.exception.CustomException;
import io.security.userapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpCustomerService {
	private final CustomerRepository customerRepository;

	public Customer signUp(SignUpForm form) {
		return customerRepository.save(Customer.from(form));
	}


	public boolean isEmailExists(String email) {
		return customerRepository.findByEmail(email.toLowerCase(Locale.ROOT)).isPresent();
	}

	@Transactional
	public LocalDateTime ChangeCustomerValidateEmail(Long customerId,String verificationCode) {
		Optional<Customer> customer = customerRepository.findById(customerId);

		if (customer.isPresent()) {
			Customer realCustomer = customer.get();
			realCustomer.setVerificationCode(verificationCode);
			realCustomer.setVerifyExpiredAt(LocalDateTime.now().plusMinutes(5));
			return realCustomer.getVerifyExpiredAt();
		}

		// todo :예외처리
		throw new CustomException(ErrorCode.USER_NOT_FOUND);

	}
}
