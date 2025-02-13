package org.newshabit.app.crawl.infrastructure.adapter;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.crawl.application.port.CrawlInputPort;
import org.newshabit.app.crawl.application.port.MessageOutputPort;
import org.newshabit.app.crawl.domain.Category;
import org.newshabit.app.crawl.domain.News;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageAdapter implements MessageOutputPort {
	private final CrawlInputPort crawlInputPort;
	private final StreamBridge streamBridge;

	@Scheduled(cron = "0 0 * * * *")
	public void sendNewsAtExactHour() {
		List<String> urls = new ArrayList<>();
		List<News> newsList = new ArrayList<>();

		for (Category category : Category.values()) {
			crawlInputPort.crawlNewsUrl(category, 10);
			newsList.addAll(crawlInputPort.crawlNewsContents(urls));
		}

		newsList.forEach(news -> {
			try {
				boolean sent = streamBridge.send("output-out-0", MessageBuilder.withPayload(news).build());
				if (!sent) {
					System.err.println("[FAIL] crawl server -> crawl topic: " + news);
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
				System.err.println("[FAIL] crawl server -> crawl topic: " + news);
			}
		});
	}
}
