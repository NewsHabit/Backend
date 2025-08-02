package org.newshabit.app.auth.infrastructure.adapter.outbound.security;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.auth.domain.model.CustomUserDetail;
import org.newshabit.app.auth.infrastructure.adapter.outbound.jwt.TokenCheckerAdapter;
import org.newshabit.app.auth.common.exception.AccessTokenException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnClass(name = "org.springframework.security.core.Authentication")
public class TokenAuthenticationProvider implements AuthenticationProvider {
	private final TokenCheckerAdapter tokenCheckerAdapter;
	private CustomUserDetail guestUserDetail;

	@PostConstruct
	public void init() {
		guestUserDetail = CustomUserDetail.createGuestUser();
	}


	@Override
	public Authentication authenticate(Authentication authentication) throws AccessTokenException {
		String accessToken = (String) authentication.getCredentials();
		CustomUserDetail userDetail = tokenCheckerAdapter.getUserDetail(accessToken).orElse(guestUserDetail);

		return new UsernamePasswordAuthenticationToken(userDetail, accessToken, userDetail.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return TokenAuthentication.class.isAssignableFrom(authentication);
	}
}

