package io.security.userapi.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SignUpForm {
	private String email;
	private String name;
	private String password;
	private LocalDate birth;
	private String phone;
}
