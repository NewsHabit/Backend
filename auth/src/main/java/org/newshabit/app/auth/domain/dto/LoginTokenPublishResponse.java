package org.newshabit.app.auth.domain.dto;

public record LoginTokenPublishResponse(
	String accessToken,
	String refreshToken
) {}
