package org.newshabit.app.common.infrastructure.auth;

import org.newshabit.app.common.domain.entity.UserEntity;
import org.newshabit.app.common.domain.enums.UserRole;
import org.newshabit.app.common.infrastructure.config.TokenAuthentication;
import org.newshabit.app.common.infrastructure.repository.UserRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnClass(name = "org.springframework.security.core.Authentication")
public class TokenAuthenticationProvider implements AuthenticationProvider {

	private final UserRepository userRepository;

	public TokenAuthenticationProvider(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String token = (String) authentication.getCredentials();

		UserEntity user = userRepository.findByAuthToken(token).orElse(null);

		if (user == null) {
			return new TokenAuthentication(token, "guest", AuthorityUtils.createAuthorityList("ROLE_GUEST"));
		}

		UserRole role = user.getUserRole();
		String authority;
		if (UserRole.ADMIN.equals(role)) {
			authority = "ROLE_ADMIN";
		} else if (UserRole.USER.equals(role)) {
			authority = "ROLE_USER";
		} else {
			authority = "ROLE_GUEST";
		}

		return new TokenAuthentication(token, user.getAuthToken(), AuthorityUtils.createAuthorityList(authority));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return TokenAuthentication.class.isAssignableFrom(authentication);
	}
}

