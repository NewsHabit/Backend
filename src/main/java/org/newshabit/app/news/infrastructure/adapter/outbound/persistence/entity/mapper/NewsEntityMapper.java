package org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.mapper;

import org.newshabit.app.news.domain.model.NewsReadLog;
import org.newshabit.app.news.domain.model.RefinedNews;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.NewsReadLogEntity;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.RefinedNewsEntity;
import org.springframework.stereotype.Component;

@Component
public class NewsEntityMapper {
	public NewsReadLogEntity toEntity(NewsReadLog newsReadLog) {
		return new NewsReadLogEntity(
			newsReadLog.getId(),
			newsReadLog.getUserId(),
			newsReadLog.getNewsId(),
			newsReadLog.getCategory(),
			newsReadLog.isTodayNews(),
			newsReadLog.getPublishedAt()
		);
	}

	public RefinedNews toDomain(RefinedNewsEntity refinedNewsEntity) {
		return new RefinedNews(
			refinedNewsEntity.getId(),
			refinedNewsEntity.getTitle(),
			refinedNewsEntity.getWhoSummary(),
			refinedNewsEntity.getWhenSummary(),
			refinedNewsEntity.getWhereSummary(),
			refinedNewsEntity.getWhatSummary(),
			refinedNewsEntity.getWhySummary(),
			refinedNewsEntity.getHowSummary(),
			refinedNewsEntity.getKeyword(),
			refinedNewsEntity.getSummary(),
			refinedNewsEntity.getPublishedAt(),
			refinedNewsEntity.getNewsCategory(),
			refinedNewsEntity.getClickCnt(),
			refinedNewsEntity.getOriginalUrl()
		);
	}

	public RefinedNewsEntity toEntity(RefinedNews refinedNews) {
		return new RefinedNewsEntity(
			refinedNews.getId(),
			refinedNews.getTitle(),
			refinedNews.getWhoSummary(),
			refinedNews.getWhenSummary(),
			refinedNews.getWhereSummary(),
			refinedNews.getWhatSummary(),
			refinedNews.getWhySummary(),
			refinedNews.getHowSummary(),
			refinedNews.getKeyword(),
			refinedNews.getSummary(),
			refinedNews.getPublishedAt(),
			refinedNews.getNewsCategory(),
			refinedNews.getClickCnt(),
			refinedNews.getOriginalUrl()
		);
	}
}
