package org.newshabit.app.user.domain.dto;

public record RegisterResponse(
	String accessToken,
	String refreshToken
) {}
