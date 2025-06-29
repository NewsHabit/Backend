package org.newshabit.app.auth.infrastructure.adapter.outbound.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.auth.infrastructure.adapter.outbound.security.utils.PemUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

@Slf4j
@Configuration
public class RSAKeyConfig {
	@Value("${auth.rsa.private-key-path}")
	private Resource privateKeyResource;

	@Value("${auth.rsa.public-key-path}")
	private Resource publicKeyResource;

	@Bean
	public RSAPrivateKey rsaPrivateKey() {
		try {
			log.info(">> privateKeyResource: desc={} exists={}", privateKeyResource.getDescription(), privateKeyResource.exists());
			String pem = readPem(privateKeyResource);
			return PemUtils.parsePrivateKey(pem);
		} catch (IOException ex) {
			throw new IllegalStateException("개인키 파일 로드 실패", ex);
		}
	}

	@Bean
	public RSAPublicKey rsaPublicKey() {
		try {
			log.info(">> publicKeyResource: desc={} exists={}", publicKeyResource.getDescription(), publicKeyResource.exists());
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
