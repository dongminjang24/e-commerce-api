package com.commerce.orderapi.domain.repository;

import java.util.List;

import com.commerce.orderapi.domain.model.Product;

public interface ProductRepositoryCustom {

	List<Product> searchByName(String name);
}
