package com.commerce.orderapi.domain.model;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import com.commerce.orderapi.domain.product.AddProductItemForm;
import com.commerce.orderapi.domain.product.UpdateProductItemForm;

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
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Audited    // 값이 변경되면 자동 저장
@AuditOverride(forClass = BaseEntity.class)
public class ProductItem extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long sellerId;

	@Audited
	private String name;

	@Audited
	private Integer price;

	private Integer count;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	public static ProductItem of(Long sellerId, AddProductItemForm form) {
		return ProductItem.builder()
			.sellerId(sellerId)
			.name(form.getName())
			.price(form.getPrice())
			.count(form.getCount())
			.build();
	}

	public void updateItem(UpdateProductItemForm form) {
		this.name = form.getName();
		this.price = form.getPrice();
		this.count = form.getCount();
	}
}