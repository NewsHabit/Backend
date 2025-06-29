package org.newshabit.app.user.infrastructure.adapter.inbound.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record DailyGoalRequest(
	@Min(value = 3, message = "일일 목표는 최소 3 이상이어야 합니다.")
	@Max(value = 5, message = "일일 목표는 최대 5 초과할 수 없습니다.")
	int dailyGoal
) {}
