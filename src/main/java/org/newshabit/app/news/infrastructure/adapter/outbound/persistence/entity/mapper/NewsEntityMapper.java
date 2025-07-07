package org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.mapper;

import org.newshabit.app.news.domain.model.Bookmark;
import org.newshabit.app.news.domain.model.NewsReadLog;
import org.newshabit.app.news.domain.model.RefinedNews;
import org.newshabit.app.news.domain.model.TodayNews;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.BookmarkEntity;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.NewsReadLogEntity;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.RefinedNewsEntity;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.TodayNewsEntity;
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

	public Bookmark toDomain(BookmarkEntity bookmarkEntity) {
		return new Bookmark(
			bookmarkEntity.getId(),
			bookmarkEntity.getNewsId(),
			bookmarkEntity.getUserId(),
			bookmarkEntity.getPublishedAt()
		);
	}

	public BookmarkEntity toEntity(Bookmark bookmark) {
		return new BookmarkEntity(
			bookmark.getId(),
			bookmark.getNewsId(),
			bookmark.getUserId(),
			bookmark.getPublishedAt()
		);
	}

	public TodayNewsEntity toEntity(TodayNews todayNews) {
		return new TodayNewsEntity(
			todayNews.getId(),
			todayNews.getNewsId(),
			todayNews.getUserId(),
			todayNews.getPublishedAt()
		);
	}

	public TodayNews toDomain(TodayNewsEntity todayNewsEntity) {
		return new TodayNews(
			todayNewsEntity.getId(),
			todayNewsEntity.getNewsId(),
			todayNewsEntity.getUserId(),
			todayNewsEntity.getPublishedAt()
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
