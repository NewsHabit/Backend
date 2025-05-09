package org.newshabit.app.auth.infrastructure.adapter.outbound.security;

import io.jsonwebtoken.Claims;
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
	private static final String DEVICE_ID_FILED_NAME = "deviceId";
	private static final String USER_ID_FILED_NAME = "userId";

	@Value("${auth.jwt.access-token.valid-time:900000}")
	private long accessTokenValidityInMilliseconds;

	@Value("${auth.jwt.refresh-token.valid-time:604800000}")
	private long refreshTokenValidityInMilliseconds;

	@PostConstruct
	public void init() {
		this.jwtParser = Jwts.parserBuilder()
			.setSigningKey(publicKey)
			.build();
	}
	
	@Override
	public String createAccessToken(String socialId, int userId, String deviceId, List<UserRole> userRoles) {
		return createToken(socialId, userId, deviceId, userRoles, accessTokenValidityInMilliseconds);
	}
	
	@Override
	public String createRefreshToken(String socialId, int userId, String deviceId, List<UserRole> userRoles) {
		return createToken(socialId, userId, deviceId, userRoles, refreshTokenValidityInMilliseconds);
	}

	@Override
	public String createCustomServerToken(String socialId, int userId, String deviceId, List<UserRole> userRoles, long tokenValidityInMilliseconds) {
		return createToken(socialId, userId, deviceId, userRoles, tokenValidityInMilliseconds);
	}

	private String createToken(String socialId, int userId, String deviceId, List<UserRole> userRoles, long validityInMilliseconds) {
		Claims claims = Jwts.claims().setSubject(socialId);
		claims.put(ROLES_FILED_NAME, userRoles.stream().map(Enum::name).toList());
		claims.put(DEVICE_ID_FILED_NAME, deviceId);
		claims.put(USER_ID_FILED_NAME, userId);
		Date now = new Date();
		Date expireDate = new Date(now.getTime() + validityInMilliseconds);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(expireDate)
			.signWith(privateKey, SignatureAlgorithm.RS256)
			.compact();
	}
}
