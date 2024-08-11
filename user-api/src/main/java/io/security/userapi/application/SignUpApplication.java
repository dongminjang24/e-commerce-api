package io.security.userapi.application;

import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import io.security.userapi.client.MailgunClient;
import io.security.userapi.client.mailgun.SendMailForm;
import io.security.userapi.domain.SignUpForm;
import io.security.userapi.domain.model.Customer;
import io.security.userapi.domain.repository.CustomerRepository;
import io.security.userapi.exception.CustomException;
import io.security.userapi.exception.ErrorCode;
import io.security.userapi.service.SignUpCustomerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpApplication {

	private final MailgunClient mailgunClient;

	private final SignUpCustomerService signUpCustomerService;

	public void customerVerify(String email,String code) {
		signUpCustomerService.verifyEmail(email,code);
	}

	public String customerSignUp(SignUpForm form) {
		if (!signUpCustomerService.isEmailExists(form.getEmail())) {
			Customer customer = signUpCustomerService.signUp(form);
			String code = getRandomCode();

			SendMailForm sendMailForm = SendMailForm.builder()
				.from("test@cocotest.com")
				.to(form.getEmail())
				.subject("Verification Email")
				.text(getVerificationEmailBody(form.getEmail(), form.getName(), code))
				.build();

			mailgunClient.sendEmail(sendMailForm);
			signUpCustomerService.ChangeCustomerValidateEmail(customer.getId(), code);
			return "회원가입에 성공하였습니다";
		} else {
			throw new CustomException(ErrorCode.ALREADY_EXISTS_USER);
		}
	}

	private String getRandomCode() {
		return RandomStringUtils.random(10, true, true);
	}

	private String getVerificationEmailBody(String email,String name, String code){
		StringBuilder builder = new StringBuilder();
		return builder.append("Hello ")
			.append(name)
			.append("! Please Click Link for verification. \n\n")
			.append("http://localhost:8081/signup/verify/customer?email=")
			.append(email)
			.append("&code=")
			.append(code)
			.toString();
	}
}
