package org.newshabit.app.auth.domain.model;

import java.util.Collection;
import java.util.List;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetail implements UserDetails {

	@Getter
	private final String username;
	@Getter
	private final String accessToken;
	private final List<GrantedAuthority> authorities;

	private CustomUserDetail(String username, String accessToken, List<GrantedAuthority> roles) {
		this.username = username;
		this.accessToken = accessToken;
		this.authorities = roles;
	}

	public static CustomUserDetail createUser(String username, String accessToken, List<String> roles) {
		List<GrantedAuthority> authorities = roles.stream()
			.map(role -> new SimpleGrantedAuthority("ROLE_" + role))
			.map(authority -> (GrantedAuthority) authority)
			.toList();
		return new CustomUserDetail(username, accessToken, authorities);
	}

	public static CustomUserDetail createGuestUser() {
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_GUEST"));
		return new CustomUserDetail(null, null, authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
