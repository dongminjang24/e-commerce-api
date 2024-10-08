package com.commerce.userapi.service.seller;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.commerce.userapi.domain.model.Seller;
import com.commerce.userapi.domain.repository.SellerRepository;

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

