package com.gto.newsHabit.global.config;

import static org.springframework.security.config.Customizer.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authz) -> authz
				.requestMatchers("/news-habit/issue", "/news-habit/recommendation")
				.permitAll()
				.anyRequest()
				.denyAll())// 나머지 요청은 모두 거부
			.httpBasic(withDefaults());// HTTP 기본 인증을 사용
		return http.build();
	}
}
