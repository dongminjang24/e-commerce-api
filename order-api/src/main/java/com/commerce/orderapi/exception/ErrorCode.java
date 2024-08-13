package com.commerce.orderapi.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
	ALREADY_EXISTS_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
	NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
	ALREADY_EXISTS_PRODUCT_ITEM(HttpStatus.BAD_REQUEST, "이미 존재하는 상품입니다.");




	private final HttpStatus httpStatus;
	private final String detail;

}
