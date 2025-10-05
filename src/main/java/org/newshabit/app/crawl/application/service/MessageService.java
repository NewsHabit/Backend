package org.newshabit.app.crawl.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.newshabit.app.avro.CrawledNews;
import org.newshabit.app.crawl.application.port.input.MessageUseCase;
import org.newshabit.app.crawl.application.port.output.CrawlPort;
import org.newshabit.app.crawl.application.port.output.MessagePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class MessageService implements MessageUseCase {

	private final MessagePort<CrawledNews> messagePort;
	private final CrawlPort crawlPort;
	@Value("${app.kafka.crawl.binding}")
	private String binding;

	@Override
	public void publishCrawledNews(List<CrawledNews> newsList) {
		if (!crawlPort.isCrawlEnabled()) {
			log.info("Crawling is disabled. Skipping publishCrawledNews.");
			return;
		}
		messagePort.publishMessages(newsList, binding);
	}
}
