package org.newshabit.app.pubsub.application.port;

import java.util.Optional;
import org.newshabit.app.avro.CrawledNews;
import org.newshabit.app.avro.RefinedNews;
import org.newshabit.app.pubsub.domain.entity.RefinedNewsEntity;

public interface RefineNewsUseCase {
	Optional<RefinedNews> refineCrawledNews(CrawledNews crawledNews);
	void sinkRefinedNews(RefinedNewsEntity refinedNewsEntity);
}
