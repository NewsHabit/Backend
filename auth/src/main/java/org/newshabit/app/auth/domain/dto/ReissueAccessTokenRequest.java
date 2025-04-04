package org.newshabit.app.auth.domain.dto;

public record ReissueAccessTokenRequest(
	String refreshToken
) {}
