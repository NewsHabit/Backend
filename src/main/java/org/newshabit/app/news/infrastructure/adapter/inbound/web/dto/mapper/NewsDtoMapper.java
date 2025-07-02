package org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.mapper;

import org.newshabit.app.news.domain.model.NewsReadLog;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.NewsReadLogEntity;
import org.springframework.stereotype.Component;

@Component
public class NewsDtoMapper {
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
