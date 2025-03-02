package org.newshabit.app.pubsub.application.usecase;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.pubsub.application.port.AiOutputPort;
import org.newshabit.app.pubsub.application.port.RefineNewsInputPort;
import org.newshabit.app.pubsub.application.port.RefinedNewsRepositoryOutputPort;
import org.newshabit.app.common.domain.model.RefinedNews;
import org.newshabit.app.common.domain.model.CrawledNews;
import org.newshabit.app.pubsub.domain.dto.AiProcessedNews;
import org.springframework.stereotype.Component;

@Slf4j
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
			AiProcessedNews aiProcessedNews = aiOutputPort.aiProcessNews(crawledNews);
			return RefinedNews.builder()
				.title(aiProcessedNews.title())
				.whoSummary(aiProcessedNews.who())
				.whenSummary(aiProcessedNews.when())
				.whereSummary(aiProcessedNews.where())
				.whatSummary(aiProcessedNews.what())
				.whySummary(aiProcessedNews.why())
				.howSummary(aiProcessedNews.how())
				.keyword(aiProcessedNews.keyword())
				.summary(aiProcessedNews.summary())
				.publishedAt(crawledNews.getCrawledTime())
				.newsCategory(crawledNews.getNewsCategory())
				.clickCnt(0L)
				.originalUrl(crawledNews.getOriginalLink()).build();
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void sinkRefinedNews(RefinedNews refinedNews) {
		refinedNewsRepositoryOutputPort.save(refinedNews);
	}
}
