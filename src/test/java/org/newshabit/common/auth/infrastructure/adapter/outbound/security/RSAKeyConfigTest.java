package org.newshabit.common.auth.infrastructure.adapter.outbound.security;

import static org.junit.jupiter.api.Assertions.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
	classes = RSAKeyConfig.class,
	properties = {
		"auth.rsa.private-key-path=classpath:private_key.pem",
		"auth.rsa.public-key-path=classpath:public_key.pem"
	}
)
class RSAKeyConfigTest {

	@Autowired
	private RSAKeyConfig rsaKeyConfig;

	@Test
	@DisplayName("RSA 개인키·공개키 빈이 정상 로드되는지 확인")
	void testKeysAreLoaded() {
		RSAPrivateKey privateKey = rsaKeyConfig.rsaPrivateKey();
		RSAPublicKey publicKey  = rsaKeyConfig.rsaPublicKey();

		assertNotNull(privateKey, "RSAPrivateKey 빈이 null 이어서는 안 됩니다.");
		assertNotNull(publicKey,  "RSAPublicKey 빈이 null 이어서는 안 됩니다.");
		assertTrue(privateKey.getAlgorithm().contains("RSA"), "로딩된 개인키 알고리즘이 RSA 여야 합니다.");
		assertTrue(publicKey.getAlgorithm().contains("RSA"),  "로딩된 공개키 알고리즘이 RSA 여야 합니다.");
	}

	@Test
	@DisplayName("로딩된 키로 JWT 서명·검증이 가능한지 확인")
	void testJwtSignAndVerify() {
		RSAPrivateKey privateKey = rsaKeyConfig.rsaPrivateKey();
		RSAPublicKey  publicKey  = rsaKeyConfig.rsaPublicKey();

		// JWT 생성 (서명)
		String token = Jwts.builder()
			.setSubject("unittest")
			.signWith(privateKey, SignatureAlgorithm.RS256)
			.compact();

		assertNotNull(token, "생성된 JWT 토큰이 null 이어서는 안 됩니다.");
		assertTrue(token.split("\\.").length == 3, "JWT는 세 부분으로 구성되어야 합니다.");

		// JWT 검증 (파싱)
		Jwts.parserBuilder()
			.setSigningKey(publicKey)
			.build()
			.parseClaimsJws(token);  // 예외가 발생하지 않아야 테스트 통과
	}
}
