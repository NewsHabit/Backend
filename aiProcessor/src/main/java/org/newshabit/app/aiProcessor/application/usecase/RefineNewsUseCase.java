package org.newshabit.app.aiProcessor.application.usecase;

import java.io.IOException;
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
		boolean exists = refinedNewsRepositoryOutputPort.existsByOriginalUrl(crawledNews.getOriginalLink());
		if (exists) {
			throw new RuntimeException("duplicate url: " + crawledNews.getOriginalLink());
		}

		try {
			return aiOutputPort.aiProcessNews(crawledNews);
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
