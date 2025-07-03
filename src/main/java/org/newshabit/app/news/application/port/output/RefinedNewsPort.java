package org.newshabit.app.news.application.port.output;

import java.time.LocalDate;
import java.util.List;
import org.newshabit.app.news.domain.model.RefinedNews;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.RefinedNewsEntity;

public interface RefinedNewsPort {
	boolean existsByOriginalUrl(String url);
	RefinedNewsEntity save(RefinedNews refinedNews);
	void deleteThresholdNews(int clickCnt, LocalDate thresholdDay);
	void updateClickCntAfterDeletion();
	RefinedNews findById(int id);
	List<RefinedNews> findAllByUserId(List<Integer> userIds);
}
