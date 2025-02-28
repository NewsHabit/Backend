package org.newshabit.app.aiProcessor.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.aiProcessor.application.port.RefinedNewsRepositoryOutputPort;
import org.newshabit.app.aiProcessor.infrastructure.repository.RefinedNewsRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RefinedNewsRepositoryAdapter implements RefinedNewsRepositoryOutputPort {
	private final RefinedNewsRepository refinedNewsRepository;

	@Override
	public boolean existsByOriginalUrl(String url) {
		return refinedNewsRepository.existsByOriginalUrl(url);
	}
}
