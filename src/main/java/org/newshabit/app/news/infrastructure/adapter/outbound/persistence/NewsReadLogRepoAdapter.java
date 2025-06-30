package org.newshabit.app.news.infrastructure.adapter.outbound.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.news.application.port.output.NewsReadLogPort;
import org.newshabit.app.news.domain.model.NewsReadLog;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.mapper.NewsDtoMapper;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository.NewsReadLogRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewsReadLogRepoAdapter implements NewsReadLogPort {
	private final NewsReadLogRepository newsReadLogRepository;
	private final NewsDtoMapper dtoMapper;

	@Override
	public void updateNewsReadLog(NewsReadLog newsReadLog) {
		if (newsReadLogRepository.findByNewsIdAndUserId(newsReadLog.getNewsId(), newsReadLog.getUserId()).isPresent()) {
			return;
		}

		newsReadLogRepository.save(dtoMapper.toEntity(newsReadLog));
	}
}
