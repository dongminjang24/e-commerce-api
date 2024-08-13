package com.commerce.orderapi.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.commerce.orderapi.domain.model.Product;
import com.commerce.orderapi.domain.product.AddProductForm;
import com.commerce.orderapi.domain.product.AddProductItemForm;
import com.commerce.orderapi.domain.repository.ProductRepository;

@SpringBootTest
class ProductServiceTest {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;

	@Test
	@Transactional
	void testAddProduct() {
		// given
		Long sellerId = 1L;
		AddProductForm form = addProductForm("나이키 에어포스", "신발입니다.", 3);

		// when
		Product p = productService.addProduct(sellerId, form);
		Product savedProduct = productRepository.findWithProductItemsById(p.getId()).get();

		// then
		assertNotNull(savedProduct);
		assertEquals(savedProduct.getId(), p.getId());
		assertEquals(savedProduct.getName(), "나이키 에어포스");
		assertEquals(savedProduct.getDescription(), "신발입니다.");
		assertEquals(savedProduct.getSellerId(), 1L);
		assertEquals(savedProduct.getProductItems().size(), 3);
		assertEquals(savedProduct.getProductItems().get(0).getName(), "나이키 에어포스0");
		assertEquals(savedProduct.getProductItems().get(0).getPrice(), 10000);
		assertEquals(savedProduct.getProductItems().get(0).getCount(), 1);
	}


	private static AddProductForm addProductForm(String name, String description,int itemCount) {
		ArrayList<AddProductItemForm> addProductItemForms = new ArrayList<>();
		for (int i = 0; i < itemCount; i++) {
			addProductItemForms.add(addProductItemForm(1L, name + i, description + i ));
		}
		return AddProductForm.builder()
				.name(name)
				.description(description)
				.items(addProductItemForms)
				.build();
	}

	private static AddProductItemForm addProductItemForm(Long productId,String name, String description) {
		return AddProductItemForm.builder()
				.productId(productId)
				.name(name)
				.price(10000)
				.count(1)
				.build();
	}

}