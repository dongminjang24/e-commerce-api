package io.security.commercedomain.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Aes256UtilTest {

	@Test
	void encrypt() {
		String encrypt = Aes256Util.encrypt("Hello world");
		assertEquals(Aes256Util.decrypt(encrypt),"Hello world");
	}

	@Test
	void decrypt() {

	}
}