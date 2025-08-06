package org.newshabit.app.news.infrastructure.adapter.inbound.web.dto;

import java.util.List;

public record TrendingNewsListResponseDto(
	List<TrendingNewsResponseDto> trendingNewsList
) { }
