package org.newshabit.app.user.domain.dto;

public record LoginResponse (
	String accessToken,
	String refreshToken
) {}
