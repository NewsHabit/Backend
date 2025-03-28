package org.newshabit.app.pubsub.infrastructure.config;

import java.util.function.Consumer;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.avro.CrawledNews;
import org.newshabit.app.avro.RefinedNews;
import org.newshabit.app.pubsub.application.port.RefineNewsUseCase;
import org.newshabit.app.common.util.SleepUtil;
import org.newshabit.app.pubsub.domain.entity.RefinedNewsEntity;
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
	public Function<CrawledNews, Message<RefinedNews>> refineNews() throws RuntimeException {
		return message -> {
			try {
				log.info("refining news start: {}", message.getOriginalLink());
				SleepUtil.randomSleep(4000, 4000);
				return refineNewsUseCase.refineCrawledNews(message)
					.map(refinedNews -> MessageBuilder.withPayload(refinedNews).build())
					.orElse(null);
			} catch (Exception e) {
				log.error("Refinement error: {}", e.getMessage());
				return null;
			}
		};
	}

	@Bean
	public Consumer<RefinedNews> sinkRefinedNews() {
		return message -> {
			try {
				refineNewsUseCase.sinkRefinedNews(RefinedNewsEntity.fromAvro(message));
			} catch (Exception e) {
				log.error("Sink error: {}", e.getMessage());
			}
		};
	}
}
