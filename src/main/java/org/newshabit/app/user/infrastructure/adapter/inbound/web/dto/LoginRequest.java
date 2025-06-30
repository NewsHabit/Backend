package org.newshabit.app.user.infrastructure.adapter.inbound.web.dto;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(
	@NotNull
	String socialId,
	@NotNull
	String deviceId
) {}
