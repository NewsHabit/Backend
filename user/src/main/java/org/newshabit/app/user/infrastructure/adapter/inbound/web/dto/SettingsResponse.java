package org.newshabit.app.user.infrastructure.adapter.inbound.web.dto;

import java.util.List;
import org.newshabit.app.common.domain.enums.NewsCategory;

public record SettingsResponse(
	String name,
	List<NewsCategory> categoryList,
	int dailyGoal
) {}
