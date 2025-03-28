package org.newshabit.app.auth.infrastructure.config;

import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnClass(name = "org.springframework.security.core.Authentication")
public class TokenAuthenticationProvider implements AuthenticationProvider {
	private final Key secretKey = Keys.hmacShaKeyFor("your-256-bit-secret-your-256-bit-secret".getBytes());

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String accessToken = (String) authentication.getCredentials();
		return new UsernamePasswordAuthenticationToken(null, accessToken, List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
//		try {
//			// JWT 파싱 및 서명 검증
//			Claims claims = Jwts.parserBuilder()
//				.setSigningKey(secretKey)
//				.build()
//				.parseClaimsJws(accessToken)
//				.getBody();
//
//			// 만료 시간 확인
//			Date expiration = claims.getExpiration();
//			if (expiration.before(new Date())) {
//				throw new AccessTokenException(ErrorMessage.EXPIRED_TOKEN);
//			}
//
//			// 사용자 정보 추출
//			String userId = claims.getSubject(); // 보통 subject에 userId를 넣음
//
//			String role = claims.get("roles", String.class);
//
//			if (role == null || role.isEmpty()) {
//				throw new AccessTokenException(ErrorMessage.MISSING_TOKEN);
//			}
//
//			List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
//
//			return new UsernamePasswordAuthenticationToken(userId, accessToken, authorities);
//		} catch (JwtException e) {
//			throw new AccessTokenException(ErrorMessage.INVALID_TOKEN);
//		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return TokenAuthentication.class.isAssignableFrom(authentication);
	}
}

