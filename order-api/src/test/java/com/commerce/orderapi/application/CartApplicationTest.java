package com.commerce.orderapi.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import com.commerce.orderapi.domain.model.Product;
import com.commerce.orderapi.domain.product.AddProductCartForm;
import com.commerce.orderapi.domain.product.AddProductForm;
import com.commerce.orderapi.domain.product.AddProductItemForm;
import com.commerce.orderapi.domain.product.UpdateProductForm;
import com.commerce.orderapi.domain.product.UpdateProductItemForm;
import com.commerce.orderapi.domain.redis.Cart;
import com.commerce.orderapi.domain.repository.ProductRepository;
import com.commerce.orderapi.domain.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class CartApplicationTest {

	@Autowired
	private CartApplication application;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;

	@Test
	void addAndRefreshTest() {
		Long customerId = 100L;
		assertNotNull(customerId, "customerId should not be null");

		// 카트 비우기 전 상태 확인
		Cart initialCart = application.getCart(customerId);
		log.info("Initial cart: {}", initialCart);

		application.clearCart(customerId);

		// 카트를 비운 후 상태 확인
		Cart clearedCart = application.getCart(customerId);
		log.info("Cleared cart: {}", clearedCart);
		assertTrue(clearedCart.getProducts().isEmpty(), "Cart products should be empty after clearing");
		assertTrue(clearedCart.getMessages().isEmpty(), "Cart messages should be empty after clearing");

		Product p = addProduct();
		Product result = productRepository.findWithProductItemsById(p.getId()).orElseThrow();
		log.info("Added product: {}", result);
		assertNotNull(result);
		assertEquals(result.getId(), p.getId());
		assertEquals(result.getName(), "나이키 에어포스");
		assertEquals(result.getDescription(), "신발");
		assertEquals(result.getSellerId(), 1L);
		assertEquals(result.getProductItems().size(), 3);
		assertEquals(result.getProductItems().get(0).getName(), "나이키 에어포스0");
		assertEquals(result.getProductItems().get(0).getPrice(), 10000);
		//        assertEquals(result.getProductItems().get(0).getCount(), 1);

		Cart cart = application.addCart(customerId, makeAddForm(result));
		log.info("Cart after adding product: {}", cart);

		// 제품을 추가한 후 카트 상태 출력
		log.info("Cart messages after adding product:");
		cart.getMessages().forEach(log::info);

		// 데이터가 잘 들어갔는지
	}

	/**
	 * 상품추가 form 생성
	 * itemCnt 만큼의 AddProductItemFrom 생성 후 삽입
	 */
	private static AddProductForm makeProductForm(String name, String description, int itemCnt) {
		List<AddProductItemForm> itemForms = new ArrayList<>();
		for (int i = 0;i < itemCnt;i++) {
			itemForms.add(makeProductItemForm(null, name + i));
		}
		return AddProductForm.builder()
			.name(name)
			.description(description)
			.items(itemForms)
			.build();
	}

	/**
	 * AddProductItemForm 생성
	 * 가격: 10000
	 * 수량 : 10
	 */
	private static AddProductItemForm makeProductItemForm(Long productId, String name) {
		return AddProductItemForm.builder()
			.productId(productId)
			.name(name)
			.price(10000)
			.count(10)
			.build();
	}





	/**
	 * 상품저장
	 * 나이키 에어포스
	 * 신발
	 * 상품 아이템 3개(가격 10000, 수량 10)
	 */
	private Product addProduct() {
		Long sellerId = 1L;

		AddProductForm form = makeProductForm("나이키 에어포스"
			,"신발"
			, 3);

		return productService.addProduct(sellerId, form);
	}

	/**
	 * Cart 생성용 form 생성
	 * AddProductCartForm.ProductItem을 넣은 AddProductCartForm을 반환
	 */
	AddProductCartForm makeAddForm(Product p) {
		AddProductCartForm.ProductItem productItem =
			AddProductCartForm.ProductItem.builder()
				.id(p.getProductItems().get(0).getId())
				.name(p.getProductItems().get(0).getName())
				.count(5)
				.price(20000)   // 변경됨
				.build();

		return AddProductCartForm.builder()
			.id(p.getId())
			.sellerId(p.getSellerId())
			.name(p.getName())
			.description(p.getDescription())
			.items(List.of(productItem))
			.build();
	}

}