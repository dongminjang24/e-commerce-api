package com.commerce.orderapi.domain.model;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import com.commerce.orderapi.domain.product.AddProductItemForm;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AuditOverride(forClass = BaseEntity.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductItem extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long sellerId;

	@Audited // audited 어노테이션은
	private String name;

	@Audited
	private Integer price;

	private Integer count;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	public static ProductItem of(Long sellerId,AddProductItemForm form) {
		return ProductItem.builder()
				.sellerId(sellerId)
				.name(form.getName())
				.price(form.getPrice())
				.count(form.getCount())
				.build();
	}
}
