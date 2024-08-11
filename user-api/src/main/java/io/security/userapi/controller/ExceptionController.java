package io.security.userapi.controller;

import java.rmi.ServerException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.security.userapi.exception.CustomException;
import io.security.userapi.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionController {

	@ExceptionHandler({ CustomException.class })
	public ResponseEntity<ExceptionResponse> customRequestException(final CustomException e) {
		log.warn("api exception : {}", e.getErrorCode());
		return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage(), e.getErrorCode()));
	}



	@Getter
	@ToString
	@AllArgsConstructor
	public static class ExceptionResponse {
		private String message;
		private ErrorCode errorCode;
	}
}
