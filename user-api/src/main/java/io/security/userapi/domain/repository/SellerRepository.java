package io.security.userapi.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.security.userapi.domain.model.Customer;
import io.security.userapi.domain.model.Seller;

@Repository
public interface SellerRepository  extends JpaRepository<Seller,Long> {

	Optional<Seller> findByEmail(String email);


}
