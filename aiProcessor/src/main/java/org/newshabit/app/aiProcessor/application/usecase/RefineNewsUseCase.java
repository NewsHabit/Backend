package org.newshabit.app.aiProcessor.application.usecase;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.aiProcessor.application.port.AiOutputPort;
import org.newshabit.app.aiProcessor.application.port.RefineNewsInputPort;
import org.newshabit.app.aiProcessor.domain.AiProcessedNews;
import org.newshabit.app.common.domain.entity.CrawledNews;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefineNewsUseCase implements RefineNewsInputPort {
	private final AiOutputPort aiOutputPort;

	@Override
	public AiProcessedNews refineCrawledNews(CrawledNews crawledNews) {
		/**
		 * 1. 뉴스 중복 탐색 -> db
		 * 2. 없다면 정제
		 */

		AiProcessedNews aiProcessedNews = aiOutputPort.aiProcessNews(crawledNews);
		return aiProcessedNews;
	}
}
