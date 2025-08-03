package org.newshabit.app.auth.domain.model;

import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@ToString
public class CustomUserDetail implements UserDetails {

	@Getter
	private final String username;
	@Getter
	private final Integer userId;
	@Getter
	private final String deviceId;
	@Getter
	private final String accessToken;
	@Getter
	private final List<String> roles;
	private final List<GrantedAuthority> authorities;

	private CustomUserDetail(String username, Integer userId, String deviceId, String accessToken, List<String> roles, List<GrantedAuthority> authorities) {
		this.username = username;
		this.userId = userId;
		this.deviceId = deviceId;
		this.accessToken = accessToken;
		this.roles = roles;
		this.authorities = authorities;
	}

	public static CustomUserDetail createUser(String username, Integer userId, String deviceId, String accessToken, List<String> roles) {
		List<GrantedAuthority> authorities = roles.stream()
			.map(role -> new SimpleGrantedAuthority("ROLE_" + role))
			.map(authority -> (GrantedAuthority) authority)
			.toList();
		return new CustomUserDetail(username, userId, deviceId, accessToken, roles, authorities);
	}

	public static CustomUserDetail createGuestUser() {
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_GUEST"));
		return new CustomUserDetail(null, null, null, null, List.of("ROLE_GUEST"), authorities);
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
