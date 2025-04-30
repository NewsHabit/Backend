package org.newshabit.app.user.infrastructure.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class FeignConfig {
	@Value("${feign.internal-access-token}")
	private String bearerToken;

	@Bean
	public RequestInterceptor newsServiceAuthInterceptor() {
		return template -> {
			template.header("Authorization", "Bearer " + bearerToken);
		};
	}
}
