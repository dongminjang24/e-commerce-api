package com.commerce.orderapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.commercedomain.common.UserVo;
import com.commerce.commercedomain.config.JwtAuthenticationProvider;
import com.commerce.orderapi.application.CartApplication;
import com.commerce.orderapi.application.OrderApplication;
import com.commerce.orderapi.domain.product.AddProductCartForm;
import com.commerce.orderapi.domain.redis.Cart;
import com.commerce.orderapi.domain.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/cart")
public class CustomerCartController  {

	private final CartApplication cartApplication;
	private final OrderApplication orderApplication;
	private final JwtAuthenticationProvider jwtAuthenticationProvider;

	@PostMapping
	public ResponseEntity<?> addCart(
		@RequestHeader("X-AUTH-TOKEN") String token,
		@RequestBody AddProductCartForm form) {
		UserVo userVo = jwtAuthenticationProvider.getUserVo(token);
		Long id = userVo.getId();
		return ResponseEntity.ok(cartApplication.addCart(id,form));
	}

	@GetMapping
	public ResponseEntity<Cart> showCart(
		@RequestHeader("X-AUTH-TOKEN") String token) {
		UserVo userVo = jwtAuthenticationProvider.getUserVo(token);
		Long id = userVo.getId();
		return ResponseEntity.ok(cartApplication.getCart(id));
	}

	@PostMapping("/order")
	public ResponseEntity<Cart> order(@RequestHeader(name = "X-AUTH-TOKEN") String token,
		@RequestBody Cart cart) {
		orderApplication.order(token, cart);
		return ResponseEntity.ok().build();
	}
}

