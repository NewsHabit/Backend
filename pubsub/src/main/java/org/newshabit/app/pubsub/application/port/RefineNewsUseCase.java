package org.newshabit.app.pubsub.application.port;

import java.util.Optional;
import org.newshabit.app.common.domain.entity.RefinedNewsEntity;
import org.newshabit.app.common.domain.model.CrawledNews;
import org.newshabit.app.common.domain.model.RefinedNews;

public interface RefineNewsUseCase {
	Optional<RefinedNews> refineCrawledNews(CrawledNews crawledNews);
	void sinkRefinedNews(RefinedNewsEntity refinedNewsEntity);
}
