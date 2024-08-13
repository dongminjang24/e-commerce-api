package com.commerce.orderapi.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import com.commerce.orderapi.domain.model.Product;
import com.commerce.orderapi.domain.model.ProductItem;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {


}
