package org.newshabit.app.crawl.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.newshabit.app.avro.CrawledNews;
import org.newshabit.app.crawl.application.port.MessageUseCase;
import org.newshabit.app.crawl.application.port.MessageOutputPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class MessageService implements MessageUseCase {
	private final MessageOutputPort<CrawledNews> messageOutputPort;

	@Value("${app.kafka.crawl.topic}")
	private String defaultTopic;

	@Override
	public void publishCrawledNews(List<CrawledNews> newsList) {
		log.info("Publishing crawled news: {}", newsList);
		messageOutputPort.publishMessages(newsList, defaultTopic);
	}
}
