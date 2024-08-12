package com.commerce.userapi.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import com.commerce.userapi.client.MailgunClient;
import com.commerce.userapi.client.mailgun.SendMailForm;

@AutoConfigureMockMvc
@SpringBootTest
class EmailSendServiceTest {

	@Autowired
	private EmailSendService emailSendService;

	@MockBean
	private MailgunClient mailgunClient;



	@Test
	public void emailTest() {

		when(mailgunClient.sendEmail(any(SendMailForm.class)))
			.thenReturn(ResponseEntity.ok("테스트 메일 발송 성공"));

		ResponseEntity<String> response = emailSendService.sendEmail();

		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody()).isEqualTo("테스트 메일 발송 성공");

		System.out.println("메일 발송 응답: " + response.getBody());
	}


}