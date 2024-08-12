package com.commerce.userapi.mock;

import com.commerce.userapi.client.MailgunClient;
import com.commerce.userapi.client.mailgun.SendMailForm;
import org.springframework.http.ResponseEntity;

public class FakeMailSender implements MailgunClient {
	@Override
	public ResponseEntity<String> sendEmail(SendMailForm form) {
		System.out.println("수신처 " + form.getTo());
		return ResponseEntity.ok("테스트 메일 발송 성공");
	}
}