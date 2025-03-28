package org.newshabit.app.crawl.application.schedule;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.newshabit.app.common.domain.model.CrawledNews;
import org.newshabit.app.crawl.application.port.CrawlUseCase;
import org.newshabit.app.crawl.application.port.MessageUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Log4j2
@Component
public class CrawlingPublisherScheduler {
	private final CrawlUseCase crawlUseCase;
	private final MessageUseCase messageUseCase;

	@Scheduled(cron = "0 0 * * * *")
	public void crawlNewsAndProduce() {
		try {
			log.info("CrawlingPublisherScheduler Started: {}", LocalDateTime.now());
			List<CrawledNews> crawledNews = crawlUseCase.crawlNews();
			messageUseCase.publishCrawledNews(crawledNews);
			log.info("CrawlingPublisherScheduler Ended: {}: count: {}", LocalDateTime.now(), crawledNews.size());
		} catch (Exception e) {
			log.error(e);
		}
	}
}
