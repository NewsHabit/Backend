package org.newshabit.app.news.infrastructure.adapter.inbound.web.dto;

import java.time.LocalDateTime;
import org.newshabit.app.common.domain.enums.NewsCategory;

public record NewsDetailResponseDto(
	 Integer id,
	 String title,
	 String whoSummary,
	 String whenSummary,
	 String whereSummary,
	 String whatSummary,
	 String whySummary,
	 String howSummary,
	 String keyword,
	 LocalDateTime publishedAt,
	 NewsCategory newsCategory
) {}
