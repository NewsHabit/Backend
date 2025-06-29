package org.newshabit.app.news.application.port.input;


import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.RefinedNewsEntity;

public interface RefineNewsConsumeUseCase {
	void sinkRefinedNews(RefinedNewsEntity refinedNewsEntity);
}
