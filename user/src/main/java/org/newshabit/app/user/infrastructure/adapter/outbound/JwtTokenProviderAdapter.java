package org.newshabit.app.user.infrastructure.adapter.outbound;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 *  jwt 를 통해서 common에서 스프링 시큐리티 구현하는 방식이 합당해보임. 
 */

@RequiredArgsConstructor
@Component
public class JwtTokenProviderAdapter {
	@Value("${app.jwt.secret-key}")
	private String jwtSecretKey;

	@Value("${app.jwt.access-token.valid-time}")
	private long accessTokenValidityInMilliseconds = 15 * 60 * 1000;

	@Value("${app.jwt.refresh-token.valid-time}")
	private long refreshTokenValidityInMilliseconds = 7 * 24 * 60 * 60 * 1000;

	public String createAccessToken(String socialId, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(socialId);
		// roles 정보를 claims에 추가
		claims.put("roles", roles);

		Date now = new Date();
		Date validity = new Date(now.getTime() + accessTokenValidityInMilliseconds);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(SignatureAlgorithm.HS256, jwtSecretKey)
			.compact();
	}

	public String createRefreshToken(String socialId) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + refreshTokenValidityInMilliseconds);

		return Jwts.builder()
			.setSubject(socialId)
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(SignatureAlgorithm.HS256, jwtSecretKey)
			.compact();
	}

	/**
	 * 토큰에서 사용자 정보를 추출합니다.
	 *
	 * @param token JWT 토큰
	 * @return 토큰의 subject (보통 username)
	 */
	public String getUsername(String token) {
		return Jwts.parser()
			.setSigningKey(jwtSecretKey)
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}
}
