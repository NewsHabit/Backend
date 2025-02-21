package org.newshabit.app.aiProcessor.infrastructure.adapter;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.aiProcessor.application.port.RefinedNewsRepositoryOutputPort;
import org.newshabit.app.aiProcessor.infrastructure.repository.RefinedNewsRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RefinedNewsRepositoryAdapter implements RefinedNewsRepositoryOutputPort {
	private final RefinedNewsRepository refinedNewsRepository;

	@Override
	public Optional<Boolean> findByUrl(String url) {
		return refinedNewsRepository.findByUrl(url);
	}
}
