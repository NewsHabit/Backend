package org.newshabit.app.user.infrastructure.adapter.inbound.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsernameUpdateRequest(
	@NotNull
	@Size(min = 1, max = 8, message = "최소 1자 이상, 최대 8자까지 입력 가능합니다.")
	String username
) {}
