package org.newshabit.app.news.infrastructure.adapter.outbound.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.news.application.port.output.NewsReadLogPort;
import org.newshabit.app.news.domain.model.NewsReadLog;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.mapper.NewsDtoMapper;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository.NewsReadLogRepo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewsReadLogAdapter implements NewsReadLogPort {
	private final NewsReadLogRepo newsReadLogRepo;
	private final NewsDtoMapper dtoMapper;

	@Override
	public void updateNewsReadLog(NewsReadLog newsReadLog) {
		if (newsReadLogRepo.findByNewsIdAndUserId(newsReadLog.getNewsId(), newsReadLog.getUserId()).isPresent()) {
			return;
		}

		newsReadLogRepo.save(dtoMapper.toEntity(newsReadLog));
	}
}
