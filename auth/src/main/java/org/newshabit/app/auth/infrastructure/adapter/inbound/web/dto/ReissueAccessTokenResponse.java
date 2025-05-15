package org.newshabit.app.auth.infrastructure.adapter.inbound.web.dto;

public record ReissueAccessTokenResponse(
	String accessToken,
	String refreshToken
) {}
