package org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.mapper;

import java.util.List;
import org.newshabit.app.news.domain.model.NewsDetail;
import org.newshabit.app.news.domain.model.NewsSimple;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.BookmarkListResponseDto;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.BookmarkResponseDto;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.NewsDetailResponseDto;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.TodayNewsListResponseDto;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.TodayNewsResponseDto;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.TrendingNewsListResponseDto;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.TrendingNewsResponseDto;
import org.springframework.stereotype.Component;

@Component
public class NewsDtoMapper {
	public TodayNewsListResponseDto toTodayNewsListResponseDto(
		List<NewsSimple> todayNewsList
	) {
		return new TodayNewsListResponseDto(
			todayNewsList.stream()
				.map(news -> new TodayNewsResponseDto(
					news.getNewsId(),
					news.getTitle(),
					news.getCategory(),
					news.getDescription()
				))
				.toList()
		);
	}

	public TrendingNewsListResponseDto toTrendingNewsListResponseDto(
		List<NewsSimple> trendingNewsList
	) {
		return new TrendingNewsListResponseDto(
			trendingNewsList.stream()
				.map(newsSimpleInfo -> new TrendingNewsResponseDto(
					newsSimpleInfo.getNewsId(),
					newsSimpleInfo.getTitle(),
					newsSimpleInfo.getCategory(),
					newsSimpleInfo.getDescription()
				))
				.toList()
		);
	}

	public BookmarkListResponseDto toBookmarkListResponseDto(List<NewsSimple> bookmarkedNewsList) {
		return new BookmarkListResponseDto(
			bookmarkedNewsList.stream()
				.map(newsSimpleInfo -> new BookmarkResponseDto(
					newsSimpleInfo.getNewsId(),
					newsSimpleInfo.getTitle(),
					newsSimpleInfo.getCategory(),
					newsSimpleInfo.getDescription()
				))
				.toList()
		);
	}

	public NewsDetailResponseDto toNewsDetailResponseDto(NewsDetail newsDetail) {
		return new NewsDetailResponseDto(
			newsDetail.getId(),
			newsDetail.getTitle(),
			newsDetail.getWhoSummary(),
			newsDetail.getWhenSummary(),
			newsDetail.getWhereSummary(),
			newsDetail.getWhatSummary(),
			newsDetail.getWhySummary(),
			newsDetail.getHowSummary(),
			newsDetail.getKeyword(),
			newsDetail.getPublishedAt(),
			newsDetail.getNewsCategory()
		);
	}
}
