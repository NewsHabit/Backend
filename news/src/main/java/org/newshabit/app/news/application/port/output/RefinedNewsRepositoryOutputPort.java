package org.newshabit.app.news.application.port.output;


import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.RefinedNewsEntity;

public interface RefinedNewsRepositoryOutputPort {
	boolean existsByOriginalUrl(String url);
	RefinedNewsEntity save(RefinedNewsEntity refinedNewsEntity);
}
