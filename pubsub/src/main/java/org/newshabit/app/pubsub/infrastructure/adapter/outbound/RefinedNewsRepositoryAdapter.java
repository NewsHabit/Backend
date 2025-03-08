package org.newshabit.app.pubsub.infrastructure.adapter.outbound;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.common.domain.model.RefinedNews;
import org.newshabit.app.pubsub.application.port.RefinedNewsRepositoryOutputPort;
import org.newshabit.app.pubsub.infrastructure.repository.RefinedNewsRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RefinedNewsRepositoryAdapter implements RefinedNewsRepositoryOutputPort {
	private final RefinedNewsRepository refinedNewsRepository;

	@Override
	public boolean existsByOriginalUrl(String url) {
		return refinedNewsRepository.existsByOriginalUrl(url);
	}

	public RefinedNews save(RefinedNews refinedNews) {
		return refinedNewsRepository.save(refinedNews);
	}
}
