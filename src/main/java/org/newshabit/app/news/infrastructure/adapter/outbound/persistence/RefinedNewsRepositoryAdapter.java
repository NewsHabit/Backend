package org.newshabit.app.news.infrastructure.adapter.outbound.persistence;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.news.application.port.output.RefinedNewsRepositoryOutputPort;
import org.newshabit.app.news.domain.model.RefinedNews;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.RefinedNewsEntity;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository.RefinedNewsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RefinedNewsRepositoryAdapter implements RefinedNewsRepositoryOutputPort {
	private final RefinedNewsRepository refinedNewsRepository;
	@Value("${app.news.delete.click_cnt}")
	private int deleteClickCount;
	@Value("${app.news.delete.date}")
	private int deleteDays;


	public boolean existsByOriginalUrl(String url) {
		return refinedNewsRepository.existsByOriginalUrl(url);
	}

	public RefinedNewsEntity save(RefinedNewsEntity refinedNewsEntity) {
		return refinedNewsRepository.save(refinedNewsEntity);
	}

	public void findDeletableNews(int clickCnt, LocalDateTime daysBeforeToday) {
		List<RefinedNewsEntity> deleteList = refinedNewsRepository.findDeletableNews(deleteClickCount, daysBeforeToday);
		refinedNewsRepository.deleteAll(deleteList);
	}

	@Override
	public List<RefinedNews> findTodayNewsByUserId(int userId) {
		return List.of();
	}

}
