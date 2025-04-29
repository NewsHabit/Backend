package org.newshabit.app.auth.infrastructure.config;

import java.time.Duration;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

@Configuration
public class AuthorizationServerConfig {
	@Bean
	public RegisteredClientRepository registeredClientRepository() {
		RegisteredClient newsClient = RegisteredClient.withId(UUID.randomUUID().toString())
			.clientId("news-service")
			.clientSecret("{noop}" + System.getenv("NEWS_CLIENT_SECRET"))
			.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
			.scope("news.read")
			.tokenSettings(TokenSettings.builder()
				.accessTokenTimeToLive(Duration.ofMinutes(60))
				.build())
			.build();
		return new InMemoryRegisteredClientRepository(newsClient);
	}
}
