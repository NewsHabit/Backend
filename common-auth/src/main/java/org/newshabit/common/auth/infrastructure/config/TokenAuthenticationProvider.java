package org.newshabit.common.auth.infrastructure.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.common.auth.domain.model.CustomUserDetail;
import org.newshabit.common.auth.infrastructure.adapter.outbound.CommonTokenCheckerAdapter;
import org.newshabit.common.auth.common.exception.AccessTokenException;
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
	private final CommonTokenCheckerAdapter commonTokenCheckerAdapter;

	@Override
	public Authentication authenticate(Authentication authentication) throws AccessTokenException {
		String accessToken = (String) authentication.getCredentials();
		CustomUserDetail userDetail = commonTokenCheckerAdapter.getUserDetail(accessToken);

		return new UsernamePasswordAuthenticationToken(userDetail, accessToken, userDetail.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return TokenAuthentication.class.isAssignableFrom(authentication);
	}
}

