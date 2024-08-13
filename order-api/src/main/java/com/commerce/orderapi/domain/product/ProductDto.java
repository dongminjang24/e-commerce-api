package com.commerce.orderapi.domain.product;

import java.util.List;

import com.commerce.orderapi.domain.model.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
	private Long id;
	private String name;
	private String description;
	private List<ProductItemDto> items;

	public static ProductDto from(Product product) {

		List<ProductItemDto> list = product.getProductItems().stream()
			.map(ProductItemDto::from)
			.toList();

		return ProductDto.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.items(list)
				.build();
	}
}
