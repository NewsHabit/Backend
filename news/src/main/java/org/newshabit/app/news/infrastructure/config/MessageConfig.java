package org.newshabit.app.news.infrastructure.config;


import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.avro.RefinedNews;
import org.newshabit.app.news.application.port.RefineNewsUseCase;
import org.newshabit.app.news.domain.entity.RefinedNewsEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MessageConfig {
	private final RefineNewsUseCase refineNewsUseCase;

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
