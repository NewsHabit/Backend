package org.newshabit.app.news.infrastructure.adapter.inbound.web.dto;

import org.newshabit.app.common.domain.enums.NewsCategory;

public record NewsReadLogRequestDto(
		int newsId,
		NewsCategory category,
		boolean isTodayNews
) { }
