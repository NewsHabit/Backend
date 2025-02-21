package org.newshabit.app.aiProcessor.infrastructure.config;

import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.aiProcessor.application.port.RefineNewsInputPort;
import org.newshabit.app.aiProcessor.domain.AiProcessedNews;
import org.newshabit.app.common.domain.entity.CrawledNews;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@Configuration
@RequiredArgsConstructor
public class MessageConfig {
	private final RefineNewsInputPort refineNewsInputPort;

	@Bean
	public Function<Message<CrawledNews>, Message<AiProcessedNews>> input() throws RuntimeException {
		return message -> MessageBuilder.withPayload(
			refineNewsInputPort.refineCrawledNews(message.getPayload())
		).build();
	}
}
