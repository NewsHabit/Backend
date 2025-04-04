package org.newshabit.app.auth.domain.dto;

public record ReissueAccessTokenResponse(
	String accessToken,
	String refreshToken
) {

}
