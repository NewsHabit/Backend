package org.newshabit.app.aiProcessor.application.port;

import java.io.IOException;
import org.newshabit.app.aiProcessor.domain.RefinedNews;
import org.newshabit.app.common.domain.entity.CrawledNews;

public interface AiOutputPort {
	RefinedNews aiProcessNews(CrawledNews crawledNews) throws IOException, InterruptedException;
}
