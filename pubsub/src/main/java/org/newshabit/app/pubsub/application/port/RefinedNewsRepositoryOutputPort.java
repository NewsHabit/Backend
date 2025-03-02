package org.newshabit.app.pubsub.application.port;

import org.newshabit.app.common.domain.model.RefinedNews;

public interface RefinedNewsRepositoryOutputPort {
	boolean existsByOriginalUrl(String url);
	RefinedNews save(RefinedNews refinedNews);
}
