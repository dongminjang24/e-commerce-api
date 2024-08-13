package com.commerce.orderapi.domain.service;

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

	@Transactional
	public Product addProduct(Long sellerId, AddProductForm form) {
		return productRepository.save(Product.of(sellerId, form));
	}

	@Transactional
	public Product updateProduct(Long sellerId, UpdateProductForm form) {
		Product product = productRepository.findBySellerIdAndId(sellerId, form.getId())
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
		product.setName(form.getName());
		product.setDescription(form.getDescription());
		List<UpdateProductItemForm> items = form.getItems();
		for (UpdateProductItemForm item : items) {
			ProductItem productItem1 = product.getProductItems()
				.stream()
				.filter(productItem -> productItem.getId().equals(item.getId()))
				.findFirst()
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT_ITEM));

			productItem1.setName(item.getName());
			productItem1.setPrice(item.getPrice());
			productItem1.setCount(item.getCount());

		}
		return product;
	}



}
