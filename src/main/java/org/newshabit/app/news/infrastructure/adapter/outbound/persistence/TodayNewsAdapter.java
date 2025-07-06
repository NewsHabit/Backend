package org.newshabit.app.news.infrastructure.adapter.outbound.persistence;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.news.application.port.output.TodayNewsPort;
import org.newshabit.app.news.domain.model.TodayNews;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.TodayNewsEntity;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository.TodayNewsRepo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TodayNewsAdapter implements TodayNewsPort {
	private final TodayNewsRepo todayNewsRepo;

	@Override
	public boolean isTodayNews(Integer userId, Integer newsId) {
		return todayNewsRepo.existsByUserIdAndNewsId(userId, newsId);
	}

	@Override
	public List<TodayNews> getTodayNewsList(Integer userId) {
		LocalDate today = LocalDate.now();

		return todayNewsRepo.findByUserIdAndPublishedAt(userId, today).stream().map(
			todayNewsEntity -> new TodayNews(
				todayNewsEntity.getId(),
				todayNewsEntity.getNewsId(),
				todayNewsEntity.getUserId(),
				todayNewsEntity.getPublishedAt()
			)
		).toList();
	}

	@Override
	public void saveTodayNewsList(List<TodayNews> todayNewsList) {
		List<TodayNewsEntity> todayNewsEntities = todayNewsList.stream().map(
			todayNews -> new TodayNewsEntity(
				todayNews.getId(),
				todayNews.getNewsId(),
				todayNews.getUserId(),
				todayNews.getPublishedAt()
			)
		).toList();

		todayNewsRepo.saveAll(todayNewsEntities);
	}
}
