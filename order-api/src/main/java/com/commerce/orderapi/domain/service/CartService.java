package com.commerce.orderapi.domain.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.commerce.orderapi.client.RedisClient;
import com.commerce.orderapi.domain.product.AddProductCartForm;
import com.commerce.orderapi.domain.redis.Cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {

	private final RedisClient redisClient;

	public Cart getCart(Long customerId) {
		return redisClient.get(customerId, Cart.class);
	}

	public Cart addProductToCart(Long customerId, AddProductCartForm form) {

		Cart cart = redisClient.get(customerId, Cart.class);
		if (cart == null) {
			Cart cart1 = new Cart();
			cart1.setCustomerId(customerId);
		}

		// 이전에 같은 상품이 있는지
		Optional<Cart.Product> productOptional = cart.getProducts().stream()
			.filter(product1 -> product1.getId().equals(form.getId()))
			.findFirst();

		if (productOptional.isPresent()) {
			Cart.Product redisProduct = productOptional.get();
			List<Cart.ProductItem> items = form.getItems()
				.stream()
				.map(Cart.ProductItem::from)
				.toList();

			Map<Long, Cart.ProductItem> redisItemMap = redisProduct.getItems().stream()
				.collect(Collectors.toMap(Cart.ProductItem::getId, item -> item));

			if (!redisProduct.getName().equals(form.getName())) {
				cart.addMessage(redisProduct.getName() + "의 정보가 변경되었습니다. 확인 부탁드립니다.");
			}

			for (Cart.ProductItem item : items) {
				Cart.ProductItem redisItem = redisItemMap.get(item.getId());
				if (redisItem == null) {
					// happy case
					redisProduct.getItems().add(item);
				} else {
					if (!redisItem.getPrice().equals(item.getPrice())) {
						cart.addMessage(redisItem.getName() + "의 가격이 변경되었습니다. 확인 부탁드립니다.");
					}
					redisItem.setCount(item.getCount() + redisItem.getCount());
				}
			}
		} else {
			Cart.Product product = Cart.Product.from(form);
			cart.getProducts().add(product);
		}
		redisClient.put(customerId, cart);
		return cart;
	}
}
