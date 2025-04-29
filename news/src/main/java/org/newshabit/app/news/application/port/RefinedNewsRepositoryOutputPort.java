package org.newshabit.app.news.application.port;


import org.newshabit.app.news.domain.entity.RefinedNewsEntity;

public interface RefinedNewsRepositoryOutputPort {
	boolean existsByOriginalUrl(String url);
	RefinedNewsEntity save(RefinedNewsEntity refinedNewsEntity);
}
