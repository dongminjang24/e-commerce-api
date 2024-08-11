package io.security.userapi.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import io.security.userapi.domain.model.CustomerBalanceHistory;

@Repository
public interface CustomerBalanceHistoryRepository extends JpaRepository<CustomerBalanceHistory,Long> {
	Optional<CustomerBalanceHistory> findFirstByCustomer_IdOrderByIdDesc(
		@RequestParam("customer_id") Long customerId);
}
