package com.commerce.orderapi.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import com.commerce.orderapi.domain.product.AddProductForm;
import com.commerce.orderapi.domain.product.UpdateProductForm;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Builder
@ToString(exclude = "productItems")
@NoArgsConstructor
@AllArgsConstructor
@Audited
@AuditOverride(forClass = BaseEntity.class)
public class Product extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long sellerId;

	private String name;

	private String description;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private List<ProductItem> productItems = new ArrayList<>();

	public static Product of(Long sellerId, AddProductForm form) {
		return Product.builder()
			.sellerId(sellerId)
			.name(form.getName())
			.description(form.getDescription())
			.productItems(
				form.getItems().stream().map(pi -> ProductItem.of(sellerId, pi))
					.collect(Collectors.toList())
			)
			.build();
	}

	public void updateProduct(UpdateProductForm form) {
		this.name = form.getName();
		this.description = form.getDescription();
	}
}
