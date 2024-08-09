package io.security.userapi.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.security.userapi.domain.SignUpForm;
import io.security.userapi.domain.model.Customer;

@SpringBootTest
class SignUpCustomerServiceTest {

	@Autowired
	public SignUpCustomerService customerService;

	@Test
	void signUp() {
		SignUpForm form = SignUpForm.builder()
			.name("name")
			.birth(LocalDate.now())
			.password("12341234")
			.email("jangdm37@gmail.com")
			.phone("01012341234")
			.build();

		Customer customer = customerService.signUp(form);
		Assertions.assertThat(customer).isNotNull();

	}
}