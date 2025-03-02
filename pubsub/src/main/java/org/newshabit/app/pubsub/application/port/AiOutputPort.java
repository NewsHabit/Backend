package org.newshabit.app.pubsub.application.port;

import java.io.IOException;
import org.newshabit.app.common.domain.model.CrawledNews;
import org.newshabit.app.pubsub.domain.dto.AiProcessedNews;

public interface AiOutputPort {
	AiProcessedNews aiProcessNews(CrawledNews crawledNews) throws IOException, InterruptedException;
}
