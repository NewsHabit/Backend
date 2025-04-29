package org.newshabit.app.aiprocess.application.service;

import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.avro.CrawledNews;
import org.newshabit.app.avro.RefinedNews;
import org.newshabit.app.aiprocess.application.port.AiOutputPort;
import org.newshabit.app.aiprocess.application.port.RefineNewsUseCase;
import org.newshabit.app.aiprocess.domain.dto.AiProcessedNews;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefineNewsService implements RefineNewsUseCase {
	private final AiOutputPort aiOutputPort;

	@Override
	public Optional<RefinedNews> refineCrawledNews(CrawledNews crawledNews) {

		// db 체크 해야함

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
				0,
				crawledNews.getOriginalLink()
			));
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
