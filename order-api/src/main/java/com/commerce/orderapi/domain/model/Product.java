package com.commerce.orderapi.domain.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import com.commerce.orderapi.domain.product.AddProductForm;

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

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Audited
@AuditOverride(forClass = BaseEntity.class)
@Table(name = "product")
public class Product extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long sellerId;

	private String name;

	private String description;

	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
	private List<ProductItem> productItems = new ArrayList<>();

	public static Product of(Long sellerId, AddProductForm form) {
		return Product.builder()
				.sellerId(sellerId)
				.name(form.getName())
				.description(form.getDescription())
				.productItems(form.getItems().stream()
						.map(item -> ProductItem.of(sellerId, item))
						.toList())
				.build();
	}
}
