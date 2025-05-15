package org.newshabit.common.auth.infrastructure.adapter.outbound.jwt;

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
import org.newshabit.common.auth.application.port.output.TokenCheckerOutputPort;
import org.newshabit.common.auth.common.exception.ErrorCode;
import org.newshabit.common.auth.domain.model.CustomUserDetail;
import org.newshabit.common.auth.common.exception.AccessTokenException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class CommonTokenCheckerAdapter implements TokenCheckerOutputPort {
	private final RSAPublicKey publicKey;

	private JwtParser jwtParser;

	private static final String ROLES_FILED_NAME = "roles";
	private static final String DEVICE_ID_FILED_NAME = "deviceId";
	private static final String USER_ID_FILED_NAME = "userId";

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
		Integer userId = claims.get(USER_ID_FILED_NAME, Integer.class);
		String deviceId = claims.get(DEVICE_ID_FILED_NAME, String.class);
		List<String> roles = getRoles(claims);

		return CustomUserDetail.createUser(socialId, userId, deviceId, token, roles);
	}

	private Claims getClaims(String token) throws AccessTokenException {
		try {
			return jwtParser.parseClaimsJws(token).getBody();
		} catch (JwtException e) {
			log.debug("JWT 파싱 실패: {}", e.getMessage(), e);
			throw new AccessTokenException(ErrorCode.INVALID_TOKEN);
		}
	}

	private void checkExpiration(Claims claims) throws AccessTokenException {
		Date expiration = claims.getExpiration();
		if (expiration.before(new Date())) {
			throw new AccessTokenException(ErrorCode.EXPIRED_TOKEN);
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
				throw new AccessTokenException(ErrorCode.AUTH_ROLE_MISSING_TOKEN);
			}
			return roles;
		}
		throw new AccessTokenException(ErrorCode.INVALID_TOKEN);
	}
}
