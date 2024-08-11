package io.security.userapi.service.seller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.security.userapi.domain.SignUpForm;
import io.security.userapi.domain.model.Customer;
import io.security.userapi.domain.model.Seller;
import io.security.userapi.domain.repository.SellerRepository;
import io.security.userapi.exception.CustomException;
import io.security.userapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerService {
	private final SellerRepository sellerRepository;

	public Optional<Seller> findByIdAndEmail(Long id,String email) {
		return sellerRepository.findById(id)
			.stream().filter(seller -> seller.getEmail().equals(email))
			.findFirst();
	}
	public Optional<Seller> findValidSeller(String email, String password) {
		return sellerRepository.findByEmail(email).stream()
			.filter(
				seller -> seller.getPassword().equals(password) && seller.isVerify()
			).findFirst();

	}




}

