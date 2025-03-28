package org.newshabit.app.auth.infrastructure.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class TokenAuthentication extends AbstractAuthenticationToken {

	private final String token;
	private final Object principal;

	public TokenAuthentication(String token) {
		super(null);
		this.token = token;
		this.principal = null;
		setAuthenticated(false);
	}

	public TokenAuthentication(String token, Object principal, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.token = token;
		this.principal = principal;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}
}
