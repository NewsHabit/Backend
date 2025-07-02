package org.newshabit.app.aiprocess.application.service;

import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.avro.CrawledNews;
import org.newshabit.app.avro.RefinedNews;
import org.newshabit.app.aiprocess.application.port.output.AiOutputPort;
import org.newshabit.app.aiprocess.application.port.input.RefineNewsUseCase;
import org.newshabit.app.aiprocess.domain.dto.AiProcessedNews;
import org.newshabit.app.news.application.port.output.RefinedNewsPort;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefineNewsService implements RefineNewsUseCase {
	private final AiOutputPort aiOutputPort;
	private final RefinedNewsPort refinedNewsPort;

	@Override
	public Optional<RefinedNews> refineCrawledNews(CrawledNews crawledNews) {
		boolean exists = refinedNewsPort.existsByOriginalUrl(crawledNews.getOriginalLink());
		if (exists) {
			log.warn("Duplicate URL detected: {}", crawledNews.getOriginalLink());
			return Optional.empty();
		}

		try {
			AiProcessedNews aiProcessedNews = aiOutputPort.aiProcessNews(crawledNews).orElse(null);
			if (aiProcessedNews == null) {
				log.warn("AI processing returned empty for URL: {}", crawledNews.getOriginalLink());
				return Optional.empty();
			}
			return Optional.of(
				new RefinedNews(
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
					0,
					crawledNews.getOriginalLink()
				)
			);
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
