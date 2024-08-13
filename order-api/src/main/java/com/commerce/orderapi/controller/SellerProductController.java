package com.commerce.orderapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.commercedomain.config.JwtAuthenticationProvider;
import com.commerce.orderapi.domain.model.Product;
import com.commerce.orderapi.domain.product.AddProductForm;
import com.commerce.orderapi.domain.product.AddProductItemForm;
import com.commerce.orderapi.domain.product.ProductDto;
import com.commerce.orderapi.domain.service.ProductItemService;
import com.commerce.orderapi.domain.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/seller/product")
@RequiredArgsConstructor
public class SellerProductController {

	private final ProductService productService;
	private final ProductItemService productItemService;
	private final JwtAuthenticationProvider jwtAuthenticationProvider;

	@PostMapping
	public ResponseEntity<?> addProduct(
		@RequestHeader("X-AUTH-TOKEN") String token,
		@RequestBody AddProductForm form) {
		Long sellerId = jwtAuthenticationProvider.getUserVo(token).getId();
		Product product = productService.addProduct(sellerId, form);
		return ResponseEntity.ok(ProductDto.from(product));
	}

	@PostMapping("/item")
	public ResponseEntity<ProductDto> addProductItem(@RequestHeader(name = "X-AUTH-TOKEN") String token,
		@RequestBody AddProductItemForm form) {
		return ResponseEntity.ok(ProductDto.from(productItemService.addProductItem(jwtAuthenticationProvider.getUserVo(token).getId(), form)));
	}





}
