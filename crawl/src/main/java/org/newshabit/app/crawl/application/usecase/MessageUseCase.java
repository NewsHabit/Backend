package org.newshabit.app.crawl.application.usecase;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.crawl.application.port.MessageInputPort;
import org.newshabit.app.crawl.application.port.MessageOutputPort;
import org.newshabit.app.crawl.domain.News;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageUseCase implements MessageInputPort {
	private final MessageOutputPort<News> messageOutputPort;


	@Override
	public void publishCrawledNews() {

	}
}
