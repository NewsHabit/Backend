package org.newshabit.common.auth.common.utils;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class PemUtils {
	public static RSAPrivateKey parsePrivateKey(String pem) {
		try {
			String clean = pem
				.replace("-----BEGIN PRIVATE KEY-----", "")
				.replace("-----END PRIVATE KEY-----", "")
				.replaceAll("\\s", "");
			byte[] decoded = Base64.getDecoder().decode(clean);
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
			return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(spec);
		} catch (Exception e) {
			throw new IllegalStateException("Invalid PEM private key", e);
		}
	}

	public static RSAPublicKey parsePublicKey(String pem) {
		try {
			String clean = pem
				.replace("-----BEGIN PUBLIC KEY-----", "")
				.replace("-----END PUBLIC KEY-----", "")
				.replaceAll("\\s", "");
			byte[] decoded = Base64.getDecoder().decode(clean);
			X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
			return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);
		} catch (Exception e) {
			throw new IllegalStateException("Invalid PEM public key", e);
		}
	}
}
