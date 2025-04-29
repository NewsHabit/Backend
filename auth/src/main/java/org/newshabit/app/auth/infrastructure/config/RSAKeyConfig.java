package org.newshabit.app.auth.infrastructure.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.newshabit.app.auth.common.utils.PemUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

@Configuration
public class RSAKeyConfig {
	@Value("${auth.rsa.private-key-path}")
	private Resource privateKeyResource;

	@Value("${auth.rsa.public-key-path}")
	private Resource publicKeyResource;

	@Bean
	public RSAPrivateKey rsaPrivateKey() {
		try {
			String pem = readPem(privateKeyResource);
			return PemUtils.parsePrivateKey(pem);
		} catch (IOException ex) {
			throw new IllegalStateException("개인키 파일 로드 실패", ex);
		}
	}

	@Bean
	public RSAPublicKey rsaPublicKey() {
		try {
			String pem = readPem(publicKeyResource);
			return PemUtils.parsePublicKey(pem);
		} catch (IOException ex) {
			throw new IllegalStateException("공개키 파일 로드 실패", ex);
		}
	}

	private String readPem(Resource resource) throws IOException {
		byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
		return new String(bytes, StandardCharsets.UTF_8);
	}
}
