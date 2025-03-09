package org.newshabit.app.pubsub.infrastructure.config;

import java.util.function.Consumer;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.common.domain.model.RefinedNews;
import org.newshabit.app.pubsub.application.port.RefineNewsUseCase;
import org.newshabit.app.common.domain.model.CrawledNews;
import org.newshabit.app.common.util.SleepUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MessageConfig {
	private final RefineNewsUseCase refineNewsUseCase;

	@Bean
	public Function<Message<CrawledNews>, Message<RefinedNews>> refineNews() throws RuntimeException {
		return message -> {
			try {
				SleepUtil.randomSleep(1000, 3000);
				return refineNewsUseCase.refineCrawledNews(message.getPayload())
					.map(refinedNews -> MessageBuilder.withPayload(refinedNews).build())
					.orElse(null);
			} catch (Exception e) {
				log.error("Refinement error: {}", e.getMessage());
				return null;
			}
		};
	}

	@Bean
	public Consumer<Message<RefinedNews>> sinkRefinedNews() {
		return message -> refineNewsUseCase.sinkRefinedNews(RefinedNews.toRefinedNewsEntity(message.getPayload()));
	}
}
