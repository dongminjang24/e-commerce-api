package io.security.userapi.domain.customer;

import io.security.userapi.domain.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomerDto {

	private Long id;
	private String email;
	public static CustomerDto from(Customer customer) {
		return new CustomerDto(customer.getId(), customer.getEmail());
	}
}
