package org.newshabit.app.news.application.port.input;

import org.newshabit.app.news.domain.model.RefinedNews;

public interface RefineNewsConsumeUseCase {
	void sinkRefinedNews(RefinedNews refinedNews);
}
