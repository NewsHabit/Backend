package org.newshabit.app.news.application.port;


import org.newshabit.app.news.domain.entity.RefinedNewsEntity;

public interface RefineNewsUseCase {
	void sinkRefinedNews(RefinedNewsEntity refinedNewsEntity);
}
