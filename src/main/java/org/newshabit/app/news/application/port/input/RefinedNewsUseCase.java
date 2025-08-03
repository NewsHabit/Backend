package org.newshabit.app.news.application.port.input;

import java.time.LocalDate;
import java.util.List;
import org.newshabit.app.news.domain.model.RefinedNews;
import org.newshabit.app.news.domain.model.TodayNewsDetail;

public interface RefinedNewsUseCase {
	List<TodayNewsDetail> getTodayNews(int userId);
	void deleteThresholdNews(int clickCntThreshold, LocalDate thresholdDay);
	List<RefinedNews> getTrendingNews(int page);
}
