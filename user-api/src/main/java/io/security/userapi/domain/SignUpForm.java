package io.security.userapi.domain;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class SignUpForm {
	private String email;
	private String name;
	private String password;
	private LocalDate birth;
	private String phone;
}
