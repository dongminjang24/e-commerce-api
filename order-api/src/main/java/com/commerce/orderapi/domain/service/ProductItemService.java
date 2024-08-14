package com.commerce.orderapi.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commerce.orderapi.domain.model.Product;
import com.commerce.orderapi.domain.model.ProductItem;
import com.commerce.orderapi.domain.product.AddProductItemForm;
import com.commerce.orderapi.domain.product.UpdateProductItemForm;
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

	public ProductItem getProductItem(Long productItemId) {
		return productItemRepository.findById(productItemId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT_ITEM));
	}

	public ProductItem saveProductItem(ProductItem productItem) {
		return productItemRepository.save(productItem);
	}

	@Transactional
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

	@Transactional
	public ProductItem updateProductItem(Long sellerId, UpdateProductItemForm form) {
		ProductItem productItem = productItemRepository.findById(form.getId())
			.filter(item -> !item.getSellerId().equals(sellerId))
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT_ITEM));

		productItem.setName(form.getName());
		productItem.setCount(form.getPrice());
		productItem.setPrice(form.getPrice());
		return productItem;
	}

	@Transactional
	public void deleteProductItem(Long sellerId, Long productItemId) {
		ProductItem productItem = productItemRepository.findById(productItemId)
			.filter(item -> item.getSellerId().equals(sellerId))
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT_ITEM));
		productItemRepository.delete(productItem);
	}
}
