package org.newshabit.app.aiProcessor.application.port;

import org.newshabit.app.aiProcessor.domain.AiProcessedNews;
import org.newshabit.app.common.domain.entity.CrawledNews;

public interface RefineNewsInputPort {
	AiProcessedNews refineCrawledNews(CrawledNews crawledNews);
}
