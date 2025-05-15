package org.newshabit.app.news.infrastructure.adapter.outbound.persistence;


import lombok.RequiredArgsConstructor;
import org.newshabit.app.news.application.port.output.RefinedNewsRepositoryOutputPort;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.RefinedNewsEntity;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository.RefinedNewsRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RefinedNewsRepositoryAdapter implements RefinedNewsRepositoryOutputPort {
	private final RefinedNewsRepository refinedNewsRepository;

	@Override
	public boolean existsByOriginalUrl(String url) {
		return refinedNewsRepository.existsByOriginalUrl(url);
	}

	public RefinedNewsEntity save(RefinedNewsEntity refinedNewsEntity) {
		return refinedNewsRepository.save(refinedNewsEntity);
	}
}
