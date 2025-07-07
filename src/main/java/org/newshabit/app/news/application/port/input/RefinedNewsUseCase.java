package org.newshabit.app.news.application.port.input;

import java.time.LocalDate;
import java.util.List;
import org.newshabit.app.news.domain.model.RefinedNews;

public interface RefinedNewsUseCase {
	List<RefinedNews> getTodayNews(int userId);
	void deleteThresholdNews(int clickCntThreshold, LocalDate thresholdDay);
	List<RefinedNews> getTrendingNews(int page);
}
