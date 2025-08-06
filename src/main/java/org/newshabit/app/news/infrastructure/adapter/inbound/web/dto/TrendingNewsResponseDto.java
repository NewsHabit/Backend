package org.newshabit.app.news.infrastructure.adapter.inbound.web.dto;

import org.newshabit.app.common.domain.enums.NewsCategory;

public record TrendingNewsResponseDto(
	int id,
	String title,
	NewsCategory category,
	String description
) {}
