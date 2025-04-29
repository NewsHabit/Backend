package org.newshabit.app.aiprocess.application.port;

import java.util.Optional;
import org.newshabit.app.avro.CrawledNews;
import org.newshabit.app.avro.RefinedNews;

public interface RefineNewsUseCase {
	Optional<RefinedNews> refineCrawledNews(CrawledNews crawledNews);
}
