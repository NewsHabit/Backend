package org.newshabit.app.auth.infrastructure.adapter.outbound;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.auth.application.port.TokenProviderOutputPort;
import org.newshabit.app.auth.domain.model.CustomUserDetail;
import org.newshabit.app.auth.infrastructure.exception.AccessTokenException;
import org.newshabit.app.auth.infrastructure.exception.AccessTokenException.ErrorMessage;
import org.newshabit.app.common.domain.enums.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtTokenProviderAdapter implements TokenProviderOutputPort {
	@Value("${app.jwt.secret-key}")
	private String secretKey;

	private Key hmacShaKey;
	private JwtParser jwtParser;
	private static final String ROLES_FILED_NAME = "roles";

	@Value("${app.jwt.access-token.valid-time}")
	private long accessTokenValidityInMilliseconds = 15 * 60 * 1000;

	@Value("${app.jwt.refresh-token.valid-time}")
	private long refreshTokenValidityInMilliseconds = 7 * 24 * 60 * 60 * 1000;

	private final CustomUserDetail guestUserDetail = CustomUserDetail.createGuestUser();

	@PostConstruct
	public void init() {
		this.hmacShaKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
		this.jwtParser = Jwts.parserBuilder()
			.setSigningKey(hmacShaKey)
			.build();
	}
	
	@Override
	public String createAccessToken(String socialId, List<UserRole> userRoles) {
		return createToken(socialId, userRoles, accessTokenValidityInMilliseconds);
	}
	
	@Override
	public String createRefreshToken(String socialId, List<UserRole> userRoles) {
		return createToken(socialId, userRoles, refreshTokenValidityInMilliseconds);
	}

	@Override
	public String reissueAccessToken(String refreshToken) throws AccessTokenException {
		if (refreshToken == null || refreshToken.isEmpty()) {
			throw new AccessTokenException(ErrorMessage.INVALID_TOKEN);
		}

		Claims claims = getClaims(refreshToken);

		checkExpiration(claims);

		String socialId = claims.getSubject();

		List<UserRole> roles = getRoles(claims).stream().map(UserRole::valueOf).toList();

		return createToken(socialId, roles, accessTokenValidityInMilliseconds);
	}

	private String createToken(String socialId, List<UserRole> userRoles, long validityInMilliseconds) {
		Claims claims = Jwts.claims().setSubject(socialId);
		List<String> roles = userRoles.stream().map(Enum::name).toList();
		claims.put(ROLES_FILED_NAME, roles);

		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(hmacShaKey)
			.compact();
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

	private Claims getClaims(String token) {
		return jwtParser.parseClaimsJws(token).getBody();

	}

	private void checkExpiration(Claims claims) throws AccessTokenException {
		Date expiration = claims.getExpiration();
		if (expiration.before(new Date())) {
			throw new AccessTokenException(ErrorMessage.EXPIRED_TOKEN);
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
				throw new AccessTokenException(ErrorMessage.INVALID_TOKEN);
			}
			return roles;
		}
		throw new AccessTokenException(ErrorMessage.INVALID_TOKEN);
	}
}
