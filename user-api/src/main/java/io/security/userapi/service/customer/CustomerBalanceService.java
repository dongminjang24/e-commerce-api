package io.security.userapi.service.customer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.security.userapi.domain.customer.ChangeBalanceForm;
import io.security.userapi.domain.model.CustomerBalanceHistory;
import io.security.userapi.domain.repository.CustomerBalanceHistoryRepository;
import io.security.userapi.domain.repository.CustomerRepository;
import io.security.userapi.exception.CustomException;
import io.security.userapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerBalanceService {

	private final CustomerBalanceHistoryRepository customerBalanceHistoryRepository;

	private final CustomerRepository customerRepository;

	@Transactional(noRollbackFor = {CustomException.class})
	public CustomerBalanceHistory changeBalance(Long customerId, ChangeBalanceForm changeBalanceForm) throws CustomException {
		CustomerBalanceHistory customerBalanceHistory = customerBalanceHistoryRepository.findFirstByCustomer_IdOrderByIdDesc(
				customerId)
			.orElse(CustomerBalanceHistory.builder()
				.changeMoney(0)
				.currentMoney(0)
				.customer(customerRepository.findById(customerId)
					.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)
					)

				).build());

		if (customerBalanceHistory.getCurrentMoney() + changeBalanceForm.getMoney() < 0) {
			throw new CustomException(ErrorCode.NOT_ENOUGH_BALANCE);
		}

		CustomerBalanceHistory.builder()
			.changeMoney(changeBalanceForm.getMoney())
			.currentMoney(customerBalanceHistory.getCurrentMoney()+changeBalanceForm.getMoney())
			.fromMessage(changeBalanceForm.getFrom())
			.description(changeBalanceForm.getMessage())
			.customer(customerBalanceHistory.getCustomer())
			.build()
		;
		customerBalanceHistory.getCustomer().setBalance(customerBalanceHistory.getCurrentMoney());

		return customerBalanceHistoryRepository.save(customerBalanceHistory);
	}


}
