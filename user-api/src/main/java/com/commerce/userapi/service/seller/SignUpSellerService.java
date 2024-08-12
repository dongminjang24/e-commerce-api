package com.commerce.userapi.service.seller;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commerce.userapi.domain.SignUpForm;
import com.commerce.userapi.domain.model.Seller;
import com.commerce.userapi.domain.repository.SellerRepository;
import com.commerce.userapi.exception.CustomException;
import com.commerce.userapi.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpSellerService {

	private final SellerRepository sellerRepository;

	public Seller signUp(SignUpForm form) {
		return sellerRepository.save(Seller.from(form));
	}


	public boolean isEmailExists(String email) {
		return sellerRepository.findByEmail(email.toLowerCase(Locale.ROOT)).isPresent();
	}

	@Transactional
	public void verifyEmail(String email, String code) {
		Seller seller = sellerRepository.findByEmail(email)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
		if (seller.isVerify()) {
			throw new CustomException(ErrorCode.ALREADY_VERIFIED_USER);
		}

		if (!seller.getVerificationCode().equals(code)) {
			throw new CustomException(ErrorCode.WRONG_VERIFICATION);
		}

		if (seller.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
			throw new CustomException(ErrorCode.EXPIRED_VERIFICATION_CODE);
		}

		seller.setVerify(true);

	}

	@Transactional
	public LocalDateTime ChangeSellerValidateEmail(Long sellerId,String verificationCode) {
		Optional<Seller> seller = sellerRepository.findById(sellerId);

		if (seller.isPresent()) {
			Seller realCustomer = seller.get();
			realCustomer.setVerificationCode(verificationCode);
			realCustomer.setVerifyExpiredAt(LocalDateTime.now().plusMinutes(5));
			return realCustomer.getVerifyExpiredAt();
		}

		// todo :예외처리
		throw new CustomException(ErrorCode.USER_NOT_FOUND);

	}



	public boolean isEmailExist(String email) {
		return sellerRepository.findByEmail(email).isPresent();
	}


	@Transactional
	public LocalDateTime changeSellerValidateEmail(Long customerId, String verificationCode) {
		Optional<Seller> customerOptional = sellerRepository.findById(customerId);

		if (customerOptional.isPresent()) {
			Seller seller = customerOptional.get();
			seller.setVerificationCode(verificationCode);
			seller.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
			return seller.getVerifyExpiredAt();
		}
		throw new CustomException(ErrorCode.USER_NOT_FOUND);
	}

}
