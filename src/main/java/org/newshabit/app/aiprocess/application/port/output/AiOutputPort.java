package org.newshabit.app.aiprocess.application.port.output;

import java.io.IOException;
import java.util.Optional;
import org.newshabit.app.avro.CrawledNews;
import org.newshabit.app.aiprocess.domain.dto.AiProcessedNews;

public interface AiOutputPort {
	Optional<AiProcessedNews> aiProcessNews(CrawledNews crawledNews) throws IOException, InterruptedException;
}
