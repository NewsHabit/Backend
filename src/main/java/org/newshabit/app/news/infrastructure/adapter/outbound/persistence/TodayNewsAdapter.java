package org.newshabit.app.news.infrastructure.adapter.outbound.persistence;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.news.application.port.output.TodayNewsPort;
import org.newshabit.app.news.domain.model.TodayNews;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository.TodayNewsRepo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TodayNewsAdapter implements TodayNewsPort {
	private final TodayNewsRepo todayNewsRepo;

	@Override
	public boolean isTodayNews(Integer newsId) {
		return todayNewsRepo.existsByNewsId(newsId);
	}

	@Override
	public List<TodayNews> getTodayNewsByUserId(Integer userId) {
		return List.of();
	}
}
