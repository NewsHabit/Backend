package org.newshabit.app.news.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.news.application.port.input.RefineNewsConsumeUseCase;
import org.newshabit.app.news.application.port.output.RefinedNewsPort;
import org.newshabit.app.news.domain.model.RefinedNews;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.RefinedNewsEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefineNewsConsumeService implements RefineNewsConsumeUseCase {
	private final RefinedNewsPort refinedNewsPort;

	@Override
	public void sinkRefinedNews(RefinedNews refinedNews) {
		RefinedNewsEntity saved = refinedNewsPort.save(refinedNews);
		log.info("sunk refined news: {}: {}", saved.getId(), saved.getOriginalUrl());
	}
}
