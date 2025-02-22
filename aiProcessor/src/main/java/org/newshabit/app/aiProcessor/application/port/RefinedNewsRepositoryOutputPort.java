package org.newshabit.app.aiProcessor.application.port;

public interface RefinedNewsRepositoryOutputPort {
	boolean existsByOriginalUrl(String url);
}
