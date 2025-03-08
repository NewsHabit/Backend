package org.newshabit.app.pubsub.application.port;

import org.newshabit.app.common.domain.entity.RefinedNewsEntity;

public interface RefinedNewsRepositoryOutputPort {
	boolean existsByOriginalUrl(String url);
	RefinedNewsEntity save(RefinedNewsEntity refinedNewsEntity);
}
