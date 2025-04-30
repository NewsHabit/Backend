package org.newshabit.app.user.domain.dto;

public record LoginTokenPublishResponse(
	String accessToken,
	String refreshToken
) {}
