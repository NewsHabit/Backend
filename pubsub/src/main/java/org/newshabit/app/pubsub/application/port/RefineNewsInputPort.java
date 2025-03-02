package org.newshabit.app.pubsub.application.port;

import org.newshabit.app.common.domain.model.RefinedNews;
import org.newshabit.app.common.domain.model.CrawledNews;

public interface RefineNewsInputPort {
	RefinedNews refineCrawledNews(CrawledNews crawledNews);
	void sinkRefinedNews(RefinedNews refinedNews);
}
