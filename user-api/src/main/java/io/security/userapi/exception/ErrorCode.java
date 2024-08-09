package io.security.userapi.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
	ALREADY_EXISTS_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
	USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다.");



	private final HttpStatus httpStatus;
	private final String detail;

}
