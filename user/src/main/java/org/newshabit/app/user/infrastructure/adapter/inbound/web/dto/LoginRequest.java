package org.newshabit.app.user.infrastructure.adapter.inbound.web.dto;

public record LoginRequest(
	String socialId,
	String deviceId
) {}
