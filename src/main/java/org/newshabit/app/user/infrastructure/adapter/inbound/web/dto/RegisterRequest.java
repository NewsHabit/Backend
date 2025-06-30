package org.newshabit.app.user.infrastructure.adapter.inbound.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.newshabit.app.common.domain.enums.NewsCategory;

public record RegisterRequest(
	@NotNull
	String socialId,

	@NotNull
	@Size(min = 1, max = 8, message = "최소 1자 이상, 최대 8자까지 입력 가능합니다.")
	String username,

	@NotNull
	@Size(min = 1, max = 5, message = "최소 1개, 최대 5개까지 선택할 수 있습니다.")
	List<@NotNull NewsCategory> categories,

	@Min(value = 3, message = "하루 목표량은 3 이상이어야 합니다.")
	@Max(value = 5, message = "하루 목표량은 5 이하여야 합니다.")
	int dailyGoal
) {}
