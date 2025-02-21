package org.newshabit.app.aiProcessor.application.port;

import org.newshabit.app.aiProcessor.domain.RefinedNews;
import org.newshabit.app.common.domain.entity.CrawledNews;

public interface RefineNewsInputPort {
	RefinedNews refineCrawledNews(CrawledNews crawledNews);
}
