package com.commerce.orderapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.commercedomain.config.JwtAuthenticationProvider;
import com.commerce.orderapi.domain.model.Product;
import com.commerce.orderapi.domain.product.ProductDto;
import com.commerce.orderapi.domain.service.ProductSearchService;
import com.commerce.orderapi.domain.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search/product")
public class SearchController {
	private final ProductSearchService productSearchService;
	private final JwtAuthenticationProvider jwtAuthenticationProvider;

	@GetMapping
	public ResponseEntity<?> searchProduct(@RequestParam String name) {
		List<ProductDto> productDtos = productSearchService.searchByName(name).stream()
			.map(ProductDto::withoutItemsFrom)
			.toList();
		return ResponseEntity.ok(productDtos);
	}

	@GetMapping("/detail")
	public ResponseEntity<?> getProductDetail(@RequestParam Long productId) {
		Product product = productSearchService.getByProductId(productId);
		ProductDto productDto = ProductDto.from(product);
		return ResponseEntity.ok(productDto);
	}

}
