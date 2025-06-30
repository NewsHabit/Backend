package org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.mapper;

import java.time.LocalDateTime;
import org.newshabit.app.news.domain.model.NewsReadLog;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.NewsReadLogRequestDto;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.NewsReadLogEntity;
import org.springframework.stereotype.Component;

@Component
public class NewsDtoMapper {
	public NewsReadLog toDomain(int userId, NewsReadLogRequestDto requestDto) {
		return new NewsReadLog(
			userId,
			requestDto.newsId(),
			requestDto.category(),
			requestDto.isTodayNews(),
			LocalDateTime.now()
		);
	}

	public NewsReadLogEntity toEntity(NewsReadLog newsReadLog) {
		return new NewsReadLogEntity(
			null,
			newsReadLog.getUserId(),
			newsReadLog.getNewsId(),
			newsReadLog.getCategory(),
			newsReadLog.isTodayNews(),
			newsReadLog.getPublishedAt()
		);
	}
}
