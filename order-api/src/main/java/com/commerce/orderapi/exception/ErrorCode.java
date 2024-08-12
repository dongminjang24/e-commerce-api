package com.commerce.orderapi.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
	ALREADY_EXISTS_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
	USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다."),
	ALREADY_VERIFIED_USER(HttpStatus.BAD_REQUEST, "이미 인증된 회원입니다."),
	WRONG_VERIFICATION(HttpStatus.BAD_REQUEST, "잘못된 인증 시도입니다."),
	EXPIRED_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, "인증 코드가 만료되었습니다."),
	NOT_ENOUGH_BALANCE(HttpStatus.BAD_REQUEST, "잔액이 부족합니다."),

	//login
	LOGIN_CHECK_FAIL(HttpStatus.BAD_REQUEST, "아이디나 패스워드를 확인해주세요");




	private final HttpStatus httpStatus;
	private final String detail;

}
