package com.commerce.orderapi.domain.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisHash;

import com.commerce.orderapi.domain.product.AddProductCartForm;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@RedisHash("cart")
public class Cart {

	@Id // @Id 어노테이션이 붙은 필드가 Redis Key값. 레디스에 저장될 최종 키 값은 keyspace : id
	private Long customerId;
	private List<Product> products = new ArrayList<>(); // 상품리스트
	private List<String> messages = new ArrayList<>();

	public Cart(Long customerId) {
		this.customerId = customerId;
	}

	public void addMessage(String message) {
		messages.add(message);
	}




	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Product {   // ProductEntity와의 중복방지
		private Long id;
		private Long sellerId;
		private String name;
		private String description;
		private List<ProductItem> items = new ArrayList<>();

		public static Product from(AddProductCartForm form) {
			return Product.builder()
				.id(form.getId())
				.sellerId(form.getSellerId())
				.name(form.getName())
				.description(form.getDescription())
				.items(form.getItems().stream()
					.map(ProductItem::from).collect(Collectors.toList()))
				.build();
		}
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ProductItem {
		private Long id;
		private String name;
		private Integer price;
		private Integer count;

		public static ProductItem from(AddProductCartForm.ProductItem form) {
			return ProductItem.builder()
				.id(form.getId())
				.name(form.getName())
				.count(form.getCount())
				.price(form.getPrice())
				.build();
		}
	}

}
