package org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.mapper;

import java.util.List;
import org.newshabit.app.news.domain.model.TodayNewsDetail;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.TodayNewsListResponseDto;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.TodayNewsResponseDto;
import org.springframework.stereotype.Component;

@Component
public class NewsDtoMapper {
	public TodayNewsListResponseDto fromDomain(
		List<TodayNewsDetail> todayNewsList
	) {
		return new TodayNewsListResponseDto(
			todayNewsList.stream()
				.map(news -> new TodayNewsResponseDto(
					news.getNewsId(),
					news.getTitle(),
					news.getCategory(),
					news.getDescription()
				))
				.toList()
		);
	}


}
