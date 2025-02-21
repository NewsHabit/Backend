package org.newshabit.app.aiProcessor.application.port;

import java.util.Optional;

public interface RefinedNewsRepositoryOutputPort {
	Optional<Boolean> findByUrl(String url);
}
