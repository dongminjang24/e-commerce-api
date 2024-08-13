package com.commerce.orderapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionAdvice {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<CustomException.CustomExceptionResponse> handleCustomException(HttpServletRequest request, final CustomException e) {
		return ResponseEntity.status(e.getStatus())
			.body(CustomException.CustomExceptionResponse.builder()
				.status(e.getStatus())
				.code(e.getErrorCode().name())
				.message(e.getMessage())
				.build());
	}
}
