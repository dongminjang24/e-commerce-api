package com.commerce.orderapi.application;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.commerce.orderapi.domain.model.Product;
import com.commerce.orderapi.domain.model.ProductItem;
import com.commerce.orderapi.domain.product.AddProductCartForm;
import com.commerce.orderapi.domain.redis.Cart;
import com.commerce.orderapi.domain.service.CartService;
import com.commerce.orderapi.domain.service.ProductSearchService;
import com.commerce.orderapi.exception.CustomException;
import com.commerce.orderapi.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartApplication {
	private final ProductSearchService productSearchService;
	private final CartService cartService;

	public Cart addProductToCart(Long customerId, AddProductCartForm form) {

		Product product = productSearchService.getByProductId(form.getId());

		if (product == null) {
			throw new CustomException(ErrorCode.NOT_FOUND_PRODUCT);
		}
		Cart cart = cartService.getCart(customerId);
		if (cart == null && !addAble(cart, product, form)) {
			throw new CustomException(ErrorCode.ITEM_COUNT_NOT_ENOUGH);
		}

		return cartService.addProductToCart(customerId, form);
	}

	private boolean addAble(Cart cart, Product product, AddProductCartForm form) {
		Cart.Product cartProduct = cart.getProducts().stream()
			.filter(p -> p.getId().equals(form.getId()))
			.findFirst()
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
		Map<Long, Integer> cartItemCountMap = cartProduct.getItems().stream()
			.collect(Collectors.toMap(Cart.ProductItem::getId, Cart.ProductItem::getCount));
		Map<Long, Integer> currentItemCountMap = product.getProductItems().stream()
			.collect(Collectors.toMap(ProductItem::getId, ProductItem::getCount));

		return form.getItems().stream()
			.noneMatch(
				formItem -> {
					Integer cartCount = cartItemCountMap.get(formItem.getId());
					Integer currentCount = currentItemCountMap.get(formItem.getId());
					return formItem.getCount() + cartCount > currentCount;
				});

	}

}
