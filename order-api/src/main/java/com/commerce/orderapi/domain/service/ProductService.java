package com.commerce.orderapi.domain.service;

import static com.commerce.orderapi.exception.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commerce.orderapi.domain.model.Product;
import com.commerce.orderapi.domain.model.ProductItem;
import com.commerce.orderapi.domain.product.AddProductForm;
import com.commerce.orderapi.domain.product.UpdateProductForm;
import com.commerce.orderapi.domain.product.UpdateProductItemForm;
import com.commerce.orderapi.domain.repository.ProductRepository;
import com.commerce.orderapi.exception.CustomException;
import com.commerce.orderapi.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;

	/**
	 * 상품 추가
	 */
	@Transactional
	public Product addProduct(Long sellerId, AddProductForm form) {
		return productRepository.save(Product.of(sellerId, form));
	}

	/**
	 * 상품 수정
	 */
	@Transactional
	public Product updateProduct(Long sellerId, UpdateProductForm form) {
		Product product = productRepository.findBySellerIdAndId(sellerId, form.getId())
			.orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));

		product.updateProduct(form);

		for (UpdateProductItemForm itemForm : form.getItems()) {
			ProductItem item = product.getProductItems().stream()
				.filter(pi -> pi.getId().equals(itemForm.getId()))
				.findFirst().orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));

			item.updateItem(itemForm);
		}

		return product;
	}

	/**
	 * 상품 삭제
	 */
	@Transactional
	public void deleteProduct(Long sellerId,Long productId) {
		Product product = productRepository.findBySellerIdAndId(sellerId, productId)
			.orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));

		productRepository.delete(product);  // cascade 설정 때문에 하위 엔티티도 같이 삭제
	}
}
