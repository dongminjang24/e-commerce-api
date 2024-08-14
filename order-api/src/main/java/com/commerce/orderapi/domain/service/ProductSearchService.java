package com.commerce.orderapi.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.commerce.orderapi.domain.model.Product;
import com.commerce.orderapi.domain.repository.ProductRepository;
import com.commerce.orderapi.exception.CustomException;
import com.commerce.orderapi.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductSearchService {
	private final ProductRepository productRepository;

	public List<Product> searchByName(String name) {
		return productRepository.searchByName(name);
	}


	public Product getByProductId(Long productId) {
		return productRepository.findWithProductItemsById(productId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
		
	}

	public List<Product> getListByProductId(List<Long> productIds) {
		return productRepository.findAllById(productIds);
	}



}
