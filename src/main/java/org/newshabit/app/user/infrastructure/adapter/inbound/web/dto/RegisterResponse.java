package org.newshabit.app.user.infrastructure.adapter.inbound.web.dto;

public record RegisterResponse(
	String accessToken,
	String refreshToken
) {}
