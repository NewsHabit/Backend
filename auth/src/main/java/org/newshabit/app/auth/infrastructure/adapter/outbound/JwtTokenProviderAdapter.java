package org.newshabit.app.auth.infrastructure.adapter.outbound;

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
import org.newshabit.common.auth.infrastructure.adapter.outbound.CommonTokenCheckerAdapter;
import org.newshabit.app.common.domain.enums.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProviderAdapter implements TokenProviderOutputPort {
	private final RSAPrivateKey privateKey;
	private final RSAPublicKey publicKey;
	private final CommonTokenCheckerAdapter commonTokenCheckerAdapter;

	private JwtParser jwtParser;
	private static final String ROLES_FILED_NAME = "roles";

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
	public String createAccessToken(String socialId, List<UserRole> userRoles) {
		return createToken(socialId, userRoles, accessTokenValidityInMilliseconds);
	}
	
	@Override
	public String createRefreshToken(String socialId, List<UserRole> userRoles) {
		return createToken(socialId, userRoles, refreshTokenValidityInMilliseconds);
	}

	@Override
	public String createCustomToken(String socialId, List<UserRole> userRoles, long tokenValidityInMilliseconds) {
		return createToken(socialId, userRoles, tokenValidityInMilliseconds);
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
}
