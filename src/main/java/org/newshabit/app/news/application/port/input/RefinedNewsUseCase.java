package org.newshabit.app.news.application.port.input;

import java.time.LocalDate;
import java.util.List;
import org.newshabit.app.news.domain.model.RefinedNews;
import org.newshabit.app.news.domain.model.NewsSimpleInfo;

public interface RefinedNewsUseCase {
	List<NewsSimpleInfo> getTodayNews(int userId);
	void deleteThresholdNews(int clickCntThreshold, LocalDate thresholdDay);
	List<NewsSimpleInfo> getTrendingNews(int page);
}
