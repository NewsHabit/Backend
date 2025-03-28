package org.newshabit.app.crawl.application.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.avro.CrawledNews;
import org.newshabit.app.avro.NewsCategory;
import org.newshabit.app.crawl.application.port.CrawlUseCase;
import org.newshabit.app.crawl.application.port.CrawlOutputPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlService implements CrawlUseCase {
	private final CrawlOutputPort crawlOutputPort;
	@Value("${app.crawl.headline-uri}")
	private String headlineUris;

	@Override
	public List<CrawledNews> crawlNews() {
		return Stream.of(NewsCategory.values())
			.parallel()
			.flatMap(category -> crawlCategoryNews(category).stream())
			.collect(Collectors.toList());
	}

	private List<CrawledNews> crawlCategoryNews(NewsCategory category) {
		log.info("Start Crawl Headlines: {}", category);

		List<String> headlines = crawlOutputPort.crawlHeadlineUris(headlineUris, category);

		List<CrawledNews> newsList = headlines.stream()
			.map(headline -> safeCrawlNews(headline, category))
			.flatMap(Optional::stream)
			.collect(Collectors.toList());

		log.info("End Crawl Headlines: Success Cnt: {}", newsList.size());

		return newsList;
	}

	private Optional<CrawledNews> safeCrawlNews(String headlineUri, NewsCategory category) {
		try {
			return Optional.of(crawlOutputPort.crawlNews(headlineUri, category));
		} catch (Exception e) {
			log.error("safeCrawlNews: {}: {}", e.getMessage(), headlineUri);
			return Optional.empty();
		}
	}
}
