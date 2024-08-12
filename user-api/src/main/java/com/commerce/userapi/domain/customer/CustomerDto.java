package com.commerce.userapi.domain.customer;

import com.commerce.userapi.domain.model.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomerDto {

	private Long id;
	private String email;
	public Integer balance;
	public static CustomerDto from(Customer customer) {
		return new CustomerDto(customer.getId(), customer.getEmail(),customer.getBalance());
	}
}
