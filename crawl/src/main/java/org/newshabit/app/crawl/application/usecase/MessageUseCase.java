package org.newshabit.app.crawl.application.usecase;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.crawl.application.port.MessageInputPort;
import org.newshabit.app.crawl.application.port.MessageOutputPort;
import org.newshabit.app.crawl.domain.News;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageUseCase implements MessageInputPort {
	private final MessageOutputPort<News> messageOutputPort;

	@Value("${app.kafka.crawl.topic}")
	private String defaultTopic;

	@Override
	public void publishCrawledNews(List<News> news) {
		messageOutputPort.publishMessages(news, defaultTopic);
	}
}
