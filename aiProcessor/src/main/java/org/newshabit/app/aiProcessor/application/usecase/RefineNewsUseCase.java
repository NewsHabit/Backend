package org.newshabit.app.aiProcessor.application.usecase;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.aiProcessor.application.port.AiOutputPort;
import org.newshabit.app.aiProcessor.application.port.RefineNewsInputPort;
import org.newshabit.app.aiProcessor.application.port.RefinedNewsRepositoryOutputPort;
import org.newshabit.app.aiProcessor.domain.RefinedNews;
import org.newshabit.app.common.domain.entity.CrawledNews;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefineNewsUseCase implements RefineNewsInputPort {
	private final AiOutputPort aiOutputPort;
	private final RefinedNewsRepositoryOutputPort refinedNewsRepositoryOutputPort;

	@Override
	public RefinedNews refineCrawledNews(CrawledNews crawledNews) {
		refinedNewsRepositoryOutputPort.findByUrl(crawledNews.getOriginalLink()).ifPresent(refinedNews -> {
			throw new RuntimeException("duplicate url: " + crawledNews.getOriginalLink());
		});

		return aiOutputPort.aiProcessNews(crawledNews);
	}
}
