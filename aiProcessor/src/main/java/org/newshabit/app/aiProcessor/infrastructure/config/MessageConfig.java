package org.newshabit.app.aiProcessor.infrastructure.config;

import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.aiProcessor.application.port.RefineNewsInputPort;
import org.newshabit.app.aiProcessor.domain.RefinedNews;
import org.newshabit.app.common.domain.entity.CrawledNews;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MessageConfig {
	private final RefineNewsInputPort refineNewsInputPort;

	@Bean
	public Function<Message<CrawledNews>, Message<RefinedNews>> refineNews() throws RuntimeException {
		return message -> {
			try {
				RefinedNews refinedNews = refineNewsInputPort.refineCrawledNews(message.getPayload());
				return MessageBuilder.withPayload(refinedNews).build();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return null;
			}
		};
	}
}
