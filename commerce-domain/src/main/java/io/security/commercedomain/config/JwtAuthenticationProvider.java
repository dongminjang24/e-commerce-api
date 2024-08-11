package io.security.commercedomain.config;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.security.commercedomain.common.UserType;
import io.security.commercedomain.common.UserVo;
import io.security.commercedomain.domain.Aes256Util;

public class JwtAuthenticationProvider {

	private String secretKey = "yourbase64encodedsecretkeyheremin44characters";
	private static final String KEY_ROLE = "roles";

	private long tokenValidaTime = 1000 * 60 * 60;

	private SecretKey getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String createToken(String userPk, Long id, UserType userType) {
		Date now = new Date();
		Date date = new Date(now.getTime() + tokenValidaTime);
		return Jwts.builder()
			.subject(Aes256Util.encrypt(userPk))
			.claim(KEY_ROLE, userType)
			.id(Aes256Util.encrypt(id.toString()))  // ID를 암호화
			.issuedAt(date)
			.expiration(date)
			.signWith(this.getSigningKey())
			.compact();
	}

	public boolean validateToken(String jwtToken) {
		try {
			Jws<Claims> claimsJws = Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(jwtToken);
			return !claimsJws.getPayload().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}

	}

	public UserVo getUserVo(String token) {
		Claims payload = Jwts.parser()
			.verifyWith(getSigningKey())
			.build()
			.parseSignedClaims(token).getPayload();

		return new UserVo(Long.parseLong(Objects.requireNonNull(Aes256Util.decrypt(payload.getId()))),
			Aes256Util.decrypt(payload.getSubject()));
	}
}
