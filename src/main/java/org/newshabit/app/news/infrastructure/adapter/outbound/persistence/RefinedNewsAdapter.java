package org.newshabit.app.news.infrastructure.adapter.outbound.persistence;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.news.application.port.output.RefinedNewsPort;
import org.newshabit.app.news.domain.model.RefinedNews;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.RefinedNewsEntity;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.mapper.NewsEntityMapper;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository.RefinedNewsRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RefinedNewsAdapter implements RefinedNewsPort {
	private final RefinedNewsRepo refinedNewsRepo;
	private final NewsEntityMapper newsEntityMapper;

	@Value("${app.news.delete.click_cnt}")
	private int deleteClickCount;
	@Value("${app.news.delete.date}")
	private int deleteDays;


	public boolean existsByOriginalUrl(String url) {
		return refinedNewsRepo.existsByOriginalUrl(url);
	}

	public RefinedNewsEntity save(RefinedNews refinedNews) {
		return refinedNewsRepo.save(newsEntityMapper.toEntity(refinedNews));
	}

	public void findDeletableNews(int clickCnt, LocalDateTime daysBeforeToday) {
		List<RefinedNewsEntity> deleteList = refinedNewsRepo.findDeletableNews(deleteClickCount, daysBeforeToday);
		refinedNewsRepo.deleteAll(deleteList);
	}

	@Override
	public RefinedNews findById(int newsId) {
		RefinedNewsEntity entity = refinedNewsRepo.findById(newsId).orElseThrow(() -> new IllegalArgumentException("News not found with newsId: " + newsId));

		return newsEntityMapper.toDomain(entity);
	}

	@Override
	public List<RefinedNews> findAllByUserId(List<Integer> userIds) {
		return List.of();
	}

}
