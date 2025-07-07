package org.newshabit.app.news.infrastructure.adapter.outbound.persistence;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.news.application.port.output.TodayNewsPort;
import org.newshabit.app.news.domain.model.TodayNews;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.TodayNewsEntity;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.mapper.NewsEntityMapper;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository.TodayNewsRepo;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class TodayNewsAdapter implements TodayNewsPort {
	private final TodayNewsRepo todayNewsRepo;
	private final NewsEntityMapper newsEntityMapper;

	@Override
	public boolean isTodayNews(Integer userId, Integer newsId) {
		return todayNewsRepo.existsByUserIdAndNewsId(userId, newsId);
	}

	@Override
	public List<TodayNews> getTodayNewsList(Integer userId) {
		LocalDate today = LocalDate.now();

		return todayNewsRepo.findByUserIdAndPublishedAt(userId, today).stream()
				.map(newsEntityMapper::toDomain)
				.toList();
	}

	@Override
	public void saveTodayNewsList(List<TodayNews> todayNewsList) {
		List<TodayNewsEntity> todayNewsEntities = todayNewsList.stream()
				.map(newsEntityMapper::toEntity)
				.toList();

		todayNewsRepo.saveAll(todayNewsEntities);
	}
}
