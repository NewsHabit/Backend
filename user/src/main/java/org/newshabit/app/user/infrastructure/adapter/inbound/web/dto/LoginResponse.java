package org.newshabit.app.user.infrastructure.adapter.inbound.web.dto;

public record LoginResponse (
	String accessToken,
	String refreshToken
) {}
