package org.newshabit.common.auth.infrastructure.adapter.outbound;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.common.auth.application.port.TokenCheckerOutputPort;
import org.newshabit.common.auth.domain.model.CustomUserDetail;
import org.newshabit.common.auth.infrastructure.exception.AccessTokenException;
import org.newshabit.common.auth.infrastructure.exception.AccessTokenException.ErrorMessage;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class CommonTokenCheckerAdapter implements TokenCheckerOutputPort {
	private final RSAPublicKey publicKey;

	private JwtParser jwtParser;

	private static final String ROLES_FILED_NAME = "roles";

	private CustomUserDetail guestUserDetail;

	@PostConstruct
	public void init() {
		this.jwtParser = Jwts.parserBuilder()
			.setSigningKey(publicKey)
			.build();
		this.guestUserDetail = CustomUserDetail.createGuestUser();
	}

	@Override
	public CustomUserDetail getUserDetail(String token) throws AccessTokenException {
		if (token == null || token.isEmpty()) {
			return guestUserDetail;
		}

		Claims claims = getClaims(token);

		checkExpiration(claims);

		String socialId = claims.getSubject();

		List<String> roles = getRoles(claims);

		return CustomUserDetail.createUser(socialId, token, roles);
	}

	private Claims getClaims(String token) throws AccessTokenException {
		try {
			return jwtParser.parseClaimsJws(token).getBody();
		} catch (JwtException e) {
			log.debug("JWT 파싱 실패: {}", e.getMessage(), e);
			throw new AccessTokenException(ErrorMessage.INVALID_TOKEN.getMessage());
		}
	}

	private void checkExpiration(Claims claims) throws AccessTokenException {
		Date expiration = claims.getExpiration();
		if (expiration.before(new Date())) {
			throw new AccessTokenException(ErrorMessage.EXPIRED_TOKEN.getMessage());
		}
	}

	private List<String> getRoles(Claims claims) throws AccessTokenException {
		Object rolesObj = claims.get(ROLES_FILED_NAME);
		if (rolesObj instanceof List<?> rolesList) {
			List<String> roles = rolesList.stream()
				.filter(String.class::isInstance)
				.map(String.class::cast)
				.toList();
			if (roles.isEmpty()) {
				throw new AccessTokenException(ErrorMessage.AUTH_ROLE_MISSING_TOKEN.getMessage());
			}
			return roles;
		}
		throw new AccessTokenException(ErrorMessage.INVALID_TOKEN.getMessage());
	}
}
