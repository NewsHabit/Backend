package org.newshabit.app.common.infrastructure.auth;

import org.newshabit.app.common.domain.entity.UserAuthEntity;
import org.newshabit.app.common.domain.enums.UserRole;
import org.newshabit.app.common.infrastructure.config.TokenAuthentication;
import org.newshabit.app.common.infrastructure.repository.UserAuthRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnClass(name = "org.springframework.security.core.Authentication")
public class TokenAuthenticationProvider implements AuthenticationProvider {

	private final UserAuthRepository userAuthRepository;

	public TokenAuthenticationProvider(UserAuthRepository userAuthRepository) {
		this.userAuthRepository = userAuthRepository;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String accessToken = (String) authentication.getCredentials();

		UserAuthEntity userAuth = userAuthRepository.findByAccessToken(accessToken).orElse(null);

		if (userAuth == null) {
			return new TokenAuthentication(accessToken, null, AuthorityUtils.createAuthorityList("ROLE_GUEST"));
		}

		UserRole role = userAuth.getUserRole();
		String authority;
		if (UserRole.ADMIN.equals(role)) {
			authority = "ROLE_ADMIN";
		} else if (UserRole.USER.equals(role)) {
			authority = "ROLE_USER";
		} else {
			authority = "ROLE_GUEST";
		}

		return new TokenAuthentication(null, userAuth.getUser(), AuthorityUtils.createAuthorityList(authority));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return TokenAuthentication.class.isAssignableFrom(authentication);
	}
}

