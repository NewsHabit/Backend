package org.newshabit.app.news.infrastructure.adapter.outbound.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.news.application.port.output.NewsReadLogPort;
import org.newshabit.app.news.domain.model.NewsReadLog;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.mapper.NewsEntityMapper;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository.NewsReadLogRepo;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewsReadLogAdapter implements NewsReadLogPort {
	private final NewsReadLogRepo newsReadLogRepo;
	private final NewsEntityMapper entityMapper;

	@Override
	public void updateNewsReadLog(NewsReadLog newsReadLog) {
		if (newsReadLogRepo.findByNewsIdAndUserId(newsReadLog.getNewsId(), newsReadLog.getUserId()).isPresent()) {
			return;
		}

		newsReadLogRepo.save(entityMapper.toEntity(newsReadLog));
	}

	@Override
	public List<NewsReadLog> findByUserIdAndDateRange(int userId, LocalDate startDate, LocalDate endDate) {
		return newsReadLogRepo.findOverlappingNewsReadLog(userId, startDate, endDate).stream()
				.map(entityMapper::toDomain)
				.toList();
	}

	@Override
	public List<NewsReadLog> findByUserId(int userId) {
		return newsReadLogRepo.findByUserIdOrderByPublishedAtAsc(userId).stream()
			.map(entityMapper::toDomain)
			.toList();
	}

	@Override
	public boolean isRead(int userId, int newsId) {
		return newsReadLogRepo.existsByUserIdAndNewsId(userId, newsId);
	}
}
