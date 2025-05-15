package org.newshabit.common.auth.infrastructure.adapter.outbound.security;

import java.security.interfaces.RSAPublicKey;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@RequiredArgsConstructor
@ConditionalOnClass(name = "org.springframework.security.config.annotation.web.builders.HttpSecurity")
public class SecurityConfig {
	private final TokenAuthenticationProvider tokenAuthenticationProvider;
	private final CustomAccessDeniedHandler accessDeniedHandler;
	private final RSAPublicKey publicKey;

	@Bean
	public JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withPublicKey(publicKey).build();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		AuthenticationManager authManager = new ProviderManager(tokenAuthenticationProvider);
		TokenAuthenticationFilter tokenFilter = new TokenAuthenticationFilter(authManager);

		http
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			// 사용자 JWT 필터
			.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)

			// 권한별 엔드포인트 접근제어
			.authorizeHttpRequests(authz -> authz
				.requestMatchers(new RegexRequestMatcher(".*/internal/.*", null)).hasRole("INTERNAL")
				.requestMatchers(new RegexRequestMatcher(".*/admin/.*", null)).hasRole("ADMIN")
				.requestMatchers(new RegexRequestMatcher(".*/member/.*", null)).hasAnyRole("MEMBER", "ADMIN")
				.requestMatchers(new RegexRequestMatcher(".*/guest/.*", null)).permitAll()
				.anyRequest().authenticated()
			)
			.exceptionHandling(config -> config
				.accessDeniedHandler(accessDeniedHandler)
			)
			.httpBasic(Customizer.withDefaults());

		return http.build();
	}
}
