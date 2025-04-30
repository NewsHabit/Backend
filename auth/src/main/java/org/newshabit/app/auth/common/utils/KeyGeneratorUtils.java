package org.newshabit.app.auth.common.utils;

import com.nimbusds.jose.jwk.RSAKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

public final class KeyGeneratorUtils {
	private KeyGeneratorUtils() {}

	/** 2048비트 RSA 키 페어 생성 */
	public static KeyPair generateRsaKey() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		return keyPairGenerator.generateKeyPair();
	}

	/** JWK 포맷으로 변환 */
	public static RSAKey generateRsa() {
		try {
			KeyPair keyPair = generateRsaKey();
			RSAPublicKey publicKey   = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			return new RSAKey.Builder(publicKey)
				.privateKey(privateKey)
				.keyID(UUID.randomUUID().toString())
				.build();
		} catch (Exception ex) {
			throw new IllegalStateException("RSA 키 생성 실패", ex);
		}
	}
}
