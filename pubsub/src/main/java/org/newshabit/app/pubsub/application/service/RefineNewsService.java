package org.newshabit.app.pubsub.application.service;

import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.common.domain.model.RefinedNews;
import org.newshabit.app.pubsub.application.port.AiOutputPort;
import org.newshabit.app.pubsub.application.port.RefineNewsUseCase;
import org.newshabit.app.pubsub.application.port.RefinedNewsRepositoryOutputPort;
import org.newshabit.app.common.domain.entity.RefinedNewsEntity;
import org.newshabit.app.common.domain.model.CrawledNews;
import org.newshabit.app.pubsub.domain.dto.AiProcessedNews;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefineNewsService implements RefineNewsUseCase {
	private final AiOutputPort aiOutputPort;
	private final RefinedNewsRepositoryOutputPort refinedNewsRepositoryOutputPort;

	@Override
	public Optional<RefinedNews> refineCrawledNews(CrawledNews crawledNews) {
		boolean exists = refinedNewsRepositoryOutputPort.existsByOriginalUrl(crawledNews.getOriginalLink());
		if (exists) {
			log.warn("duplicate url: {}", crawledNews.getOriginalLink());
			return Optional.empty();
		}

		try {
			Optional<AiProcessedNews> aiProcessedNewsOptional = aiOutputPort.aiProcessNews(crawledNews);
			return aiProcessedNewsOptional.map(aiProcessedNews -> new RefinedNews(
				aiProcessedNews.title(),
				aiProcessedNews.who(),
				aiProcessedNews.when(),
				aiProcessedNews.where(),
				aiProcessedNews.what(),
				aiProcessedNews.why(),
				aiProcessedNews.how(),
				aiProcessedNews.keyword(),
				aiProcessedNews.summary(),
				crawledNews.getCrawledTime(),
				crawledNews.getNewsCategory(),
				0L,
				crawledNews.getOriginalLink()
			));
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void sinkRefinedNews(RefinedNewsEntity refinedNewsEntity) {
		refinedNewsRepositoryOutputPort.save(refinedNewsEntity);
	}
}
