package org.newshabit.app.news.infrastructure.adapter.inbound.stream.mapper;

import java.time.LocalDateTime;
import org.newshabit.app.avro.RefinedNews;
import org.newshabit.app.common.domain.enums.NewsCategory;
import org.springframework.stereotype.Component;

@Component
public class AvroMapper {
	public org.newshabit.app.news.domain.model.RefinedNews fromAvro(RefinedNews refinedNews) {
		return new org.newshabit.app.news.domain.model.RefinedNews(
			null,
			refinedNews.getTitle(),
			refinedNews.getWhoSummary(),
			refinedNews.getWhenSummary(),
			refinedNews.getWhereSummary(),
			refinedNews.getWhatSummary(),
			refinedNews.getWhySummary(),
			refinedNews.getHowSummary(),
			refinedNews.getKeyword(),
			refinedNews.getSummary(),
			LocalDateTime.now(),
			NewsCategory.valueOf(refinedNews.getNewsCategory().name()),
			refinedNews.getClickCnt(),
			refinedNews.getOriginalUrl()
		);
	}
}
