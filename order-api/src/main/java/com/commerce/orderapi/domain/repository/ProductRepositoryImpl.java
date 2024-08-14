package com.commerce.orderapi.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.commerce.orderapi.domain.model.Product;
import com.commerce.orderapi.domain.model.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Product> searchByName(String name) {
		String search = "%" + name + "%";
		QProduct product = QProduct.product;
		return jpaQueryFactory.selectFrom(product)
			.where(product.name.like(search))
			.fetch();
	}
}
