package org.newshabit.app.auth.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.auth.domain.model.CustomUserDetail;
import org.newshabit.app.auth.infrastructure.adapter.outbound.JwtTokenProviderAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnClass(name = "org.springframework.security.core.Authentication")
public class TokenAuthenticationProvider implements AuthenticationProvider {
	private final JwtTokenProviderAdapter jwtTokenProviderAdapter;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String accessToken = (String) authentication.getCredentials();
		CustomUserDetail userDetail = jwtTokenProviderAdapter.getUserDetail(accessToken);

		return new UsernamePasswordAuthenticationToken(userDetail, accessToken, userDetail.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return TokenAuthentication.class.isAssignableFrom(authentication);
	}
}

