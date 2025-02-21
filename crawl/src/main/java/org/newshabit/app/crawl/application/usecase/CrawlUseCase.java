package org.newshabit.app.crawl.application.usecase;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.common.domain.entity.CrawledNews;
import org.newshabit.app.common.domain.enums.NewsCategory;
import org.newshabit.app.crawl.application.port.CrawlInputPort;
import org.newshabit.app.crawl.application.port.CrawlOutputPort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlUseCase implements CrawlInputPort {
	private final CrawlOutputPort crawlOutputPort;
	private static final String CRAWL_URL = "https://news.naver.com/section/";

	@Override
	public List<CrawledNews> crawlNews() {
		return Stream.of(NewsCategory.values())
			.parallel()
			.flatMap(category -> crawlCategoryNews(category).stream())
			.collect(Collectors.toList());
	}

	private List<CrawledNews> crawlCategoryNews(NewsCategory category) {
		log.info("Start Crawl Headlines: {}", category);

		List<String> headlines = crawlOutputPort.crawlHeadlineUrls(CRAWL_URL, category);

		List<CrawledNews> newsList = headlines.stream()
			.map(headline -> safeCrawlNews(headline, category))
			.flatMap(Optional::stream)
			.collect(Collectors.toList());

		log.info("End Crawl Headlines: Success Cnt: {}", newsList.size());

		return newsList;
	}

	private Optional<CrawledNews> safeCrawlNews(String headlineUrl, NewsCategory category) {
		try {
			return Optional.ofNullable(crawlOutputPort.crawlNews(headlineUrl, category));
		} catch (Exception e) {
			log.error("{}: {}", e.getMessage(), headlineUrl);
			return Optional.empty();
		}
	}
}
