package org.newshabit.app.news.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.news.application.port.input.RefineNewsConsumeUseCase;
import org.newshabit.app.news.application.port.output.RefinedNewsRepositoryOutputPort;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.RefinedNewsEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefineNewsConsumeService implements RefineNewsConsumeUseCase {
	private final RefinedNewsRepositoryOutputPort refinedNewsRepositoryOutputPort;

	@Override
	public void sinkRefinedNews(RefinedNewsEntity refinedNewsEntity) {
		RefinedNewsEntity saved = refinedNewsRepositoryOutputPort.save(refinedNewsEntity);
		log.info("sinked refined news: {}: {}", saved.getId(), saved.getOriginalUrl());
	}
}
