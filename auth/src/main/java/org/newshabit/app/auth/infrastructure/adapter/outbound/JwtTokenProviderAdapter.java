package org.newshabit.app.auth.infrastructure.adapter.outbound;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.auth.application.port.TokenProviderOutputPort;
import org.newshabit.app.auth.domain.model.CustomUserDetail;
import org.newshabit.app.auth.infrastructure.exception.AccessTokenException;
import org.newshabit.app.auth.infrastructure.exception.AccessTokenException.ErrorMessage;
import org.newshabit.app.common.domain.enums.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProviderAdapter implements TokenProviderOutputPort {
	private final RSAPrivateKey privateKey;
	private final RSAPublicKey publicKey;

	private JwtParser jwtParser;
	private static final String ROLES_FILED_NAME = "roles";

	@Value("${auth.jwt.access-token.valid-time:900000}")
	private long accessTokenValidityInMilliseconds;

	@Value("${auth.jwt.refresh-token.valid-time:604800000}")
	private long refreshTokenValidityInMilliseconds;

	private CustomUserDetail guestUserDetail;

	@PostConstruct
	public void init() {
		this.jwtParser = Jwts.parserBuilder()
			.setSigningKey(publicKey)
			.build();
		this.guestUserDetail = CustomUserDetail.createGuestUser();
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
			throw new AccessTokenException(ErrorMessage.INVALID_TOKEN.getMessage());
		}

		Claims claims = getClaims(refreshToken);

		checkExpiration(claims);

		String socialId = claims.getSubject();

		List<UserRole> roles = getRoles(claims).stream().map(UserRole::valueOf).toList();

		return createToken(socialId, roles, accessTokenValidityInMilliseconds);
	}

	private String createToken(String socialId, List<UserRole> userRoles, long validityInMilliseconds) {
		Claims claims = Jwts.claims().setSubject(socialId);
		claims.put(ROLES_FILED_NAME, userRoles.stream().map(Enum::name).toList());

		Date now = new Date();
		Date expireDate = new Date(now.getTime() + validityInMilliseconds);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(expireDate)
			.signWith(privateKey, SignatureAlgorithm.RS256)
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

	private Claims getClaims(String token) throws AccessTokenException {
		try {
			return jwtParser.parseClaimsJws(token).getBody();
		} catch (JwtException e) {
			log.error("JWT 파싱 실패: {}", e.getMessage(), e);
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
