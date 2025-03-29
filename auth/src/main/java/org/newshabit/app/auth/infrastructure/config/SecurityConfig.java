package org.newshabit.app.auth.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@ConditionalOnClass(name = "org.springframework.security.config.annotation.web.builders.HttpSecurity")
public class SecurityConfig {

	private final TokenAuthenticationProvider tokenAuthenticationProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// 커스텀 AuthenticationManager 생성
		AuthenticationManager authManager = new ProviderManager(tokenAuthenticationProvider);

		// 커스텀 토큰 필터 생성 및 등록
		TokenAuthenticationFilter tokenFilter = new TokenAuthenticationFilter(authManager);

		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(authz -> authz
				.requestMatchers("/v1/admin/**").hasRole("ADMIN")
				.requestMatchers("/v1/user/**").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/v1/guest/**").permitAll()
				.anyRequest().authenticated()
			)
			.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
			.httpBasic(Customizer.withDefaults());

		return http.build();
	}
}
