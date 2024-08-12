package com.commerce.userapi.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commerce.userapi.domain.model.Seller;

@Repository
public interface SellerRepository  extends JpaRepository<Seller,Long> {

	Optional<Seller> findByEmail(String email);


}
