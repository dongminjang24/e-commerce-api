package com.commerce.orderapi.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import com.commerce.orderapi.domain.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> , ProductRepositoryCustom{

	@EntityGraph(attributePaths = "productItems",type = EntityGraph.EntityGraphType.LOAD)
	Optional<Product> findWithProductItemsById(Long id);

	@EntityGraph(attributePaths = "productItems",type = EntityGraph.EntityGraphType.LOAD)
	Optional<Product> findBySellerIdAndId(Long id, Long sellerId);


	@EntityGraph(attributePaths = {"productItems"}, type = EntityGraph.EntityGraphType.LOAD)
	List<Product> findAllByIdIn(List<Long> ids);


}
