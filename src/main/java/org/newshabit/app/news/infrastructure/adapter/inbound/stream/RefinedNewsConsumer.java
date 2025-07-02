package org.newshabit.app.news.infrastructure.adapter.inbound.stream;


import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.avro.RefinedNews;
import org.newshabit.app.news.application.port.input.RefineNewsConsumeUseCase;
import org.newshabit.app.news.infrastructure.adapter.inbound.stream.mapper.AvroMapper;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.RefinedNewsEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RefinedNewsConsumer {
	private final RefineNewsConsumeUseCase refineNewsConsumeUseCase;
	private final AvroMapper avroMapper;

	@Bean
	public Consumer<RefinedNews> sinkRefinedNews() {
		return message -> {
			try {
				refineNewsConsumeUseCase.sinkRefinedNews(avroMapper.fromAvro(message));
			} catch (Exception e) {
				log.error("Sink error: {}", e.getMessage());
			}
		};
	}
}
