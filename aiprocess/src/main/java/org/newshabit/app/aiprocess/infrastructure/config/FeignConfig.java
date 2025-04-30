package org.newshabit.app.aiprocess.infrastructure.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class FeignConfig {
	@Value("${feign.internal-access-token}")
	private String bearerToken;

	@Bean
	public RequestInterceptor newsServiceAuthInterceptor() {
		return template -> {
			// 모든 Feign 요청에 Authorization 헤더를 추가
			template.header("Authorization", "Bearer " + bearerToken);
		};
	}
}
