package com.commerce.orderapi.application;

import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commerce.orderapi.client.UserClient;
import com.commerce.orderapi.client.user.ChangeBalanceForm;
import com.commerce.orderapi.client.user.CustomerDto;
import com.commerce.orderapi.domain.model.ProductItem;
import com.commerce.orderapi.domain.redis.Cart;
import com.commerce.orderapi.domain.service.ProductItemService;
import com.commerce.orderapi.exception.CustomException;
import com.commerce.orderapi.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderApplication {
	private final CartApplication cartApplication;
	private final UserClient userClient;
	private final ProductItemService productItemService;
	@Transactional
	public void order(String token, Cart cart) {

		Cart orderCart = cartApplication.refreshCart(cart);

		if(!orderCart.getMessages().isEmpty()) {
			throw new CustomException(ErrorCode.NOT_FOUND_PRODUCT);
		}

		Integer totalPrice = getTotalPrice(cart);
		CustomerDto customerDto = userClient.getCustomerInfo(token).getBody();

		if(customerDto.getBalance() < getTotalPrice(cart)) {
			throw new CustomException(ErrorCode.ORDER_FAIL_NOT_ENOUGH_BALANCE);
		}
		// 롤백 계획에 대해서 생갹해야함
		userClient.changeBalance(token, ChangeBalanceForm.builder()
			.from("USER")
			.message("Order")
			.money(-totalPrice)
			.build());


		for (Cart.Product product : orderCart.getProducts()) {
			for (Cart.ProductItem item : product.getItems()) {
				ProductItem productItem =
					productItemService.getProductItem(item.getId());
				productItem.setCount(productItem.getCount() - item.getCount());
			}
		}

	}

	private Integer getTotalPrice(Cart cart) {
		return cart.getProducts().stream().flatMapToInt(product ->
				product.getItems().stream().flatMapToInt(productItem ->
					IntStream.of(productItem.getPrice() * productItem.getCount())))
			.sum();
	}
}
