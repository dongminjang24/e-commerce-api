package com.commerce.userapi.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.commerce.userapi.client.mailgun.SendMailForm;

import com.commerce.userapi.client.MailgunClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailSendService {
	private final MailgunClient mailgunClient;

	public ResponseEntity<String> sendEmail() {
		SendMailForm sendMailForm = SendMailForm.builder()
			.from("commerce-coco@test.com")
			.to("jangdm37@naver.com")
			.subject("Test email commerce test")
			.text("test Texting").build();
		return this.mailgunClient.sendEmail(sendMailForm);
	}
}
