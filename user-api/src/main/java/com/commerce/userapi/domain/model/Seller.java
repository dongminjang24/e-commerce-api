package com.commerce.userapi.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.commerce.userapi.domain.BaseEntity;
import com.commerce.userapi.domain.SignUpForm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seller extends BaseEntity {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;
	private String name;
	private String password;
	private LocalDate birth;
	private String phone;

	private LocalDateTime verifyExpiredAt;
	private String verificationCode;
	private boolean verify;

	public static Seller from(SignUpForm form) {
		return Seller.builder()
			.email(form.getEmail())
			.name(form.getName())
			.birth(form.getBirth())
			.password(form.getPassword())
			.phone(form.getPhone())
			.verify(false
			)
			.build();
	}
}
