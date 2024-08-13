package com.commerce.orderapi.domain.service;

import org.springframework.stereotype.Service;

import com.commerce.orderapi.domain.model.Product;
import com.commerce.orderapi.domain.model.ProductItem;
import com.commerce.orderapi.domain.product.AddProductItemForm;
import com.commerce.orderapi.domain.repository.ProductItemRepository;
import com.commerce.orderapi.domain.repository.ProductRepository;
import com.commerce.orderapi.exception.CustomException;
import com.commerce.orderapi.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductItemService {

	private final ProductRepository productRepository;
	private final ProductItemRepository productItemRepository;

	public Product addProductItem(Long sellerId, AddProductItemForm form) {
		Product product = productRepository.findBySellerIdAndId(sellerId, form.getProductId())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));

		if(product.getProductItems().stream().anyMatch(item -> item.getName().equals(form.getName()))) {
			throw new CustomException(ErrorCode.ALREADY_EXISTS_PRODUCT_ITEM);
		}
		ProductItem productItem = ProductItem.of(sellerId, form);
		product.getProductItems().add(productItem);
		return product;
	}
}
