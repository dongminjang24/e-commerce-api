package io.security.commercedomain.domain;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Aes256Util {

	public static String alg = "AES/CBC/PKCS5Padding";
	private static final String KEY = "COCOLOVEILLBEYOU";
	private static final String IV = KEY.substring(0, 16);

	public static String encrypt(String text) {
		try {
			Cipher cipher = Cipher.getInstance(alg);
			SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
			byte[] encrypt = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
			return Base64.encodeBase64String(encrypt);
		} catch (Exception e) {
			return null;
		}
	}

	public static String decrypt(String cipherText) {
		try {
			Cipher cipher = Cipher.getInstance(alg);
			SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));

			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

			byte[] decodeBase64 = Base64.decodeBase64(cipherText);
			byte[] decrypted = cipher.doFinal(decodeBase64);
			return new String(decrypted, StandardCharsets.UTF_8);

		} catch (Exception e) {
			return null;
		}
	}

}
