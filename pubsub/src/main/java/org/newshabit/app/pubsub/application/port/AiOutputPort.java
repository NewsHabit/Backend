package org.newshabit.app.pubsub.application.port;

import java.io.IOException;
import java.util.Optional;
import org.newshabit.app.avro.CrawledNews;
import org.newshabit.app.pubsub.domain.dto.AiProcessedNews;

public interface AiOutputPort {
	Optional<AiProcessedNews> aiProcessNews(CrawledNews crawledNews) throws IOException, InterruptedException;
}
