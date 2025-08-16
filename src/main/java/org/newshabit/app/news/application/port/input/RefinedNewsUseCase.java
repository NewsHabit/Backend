package org.newshabit.app.news.application.port.input;

import java.time.LocalDate;
import java.util.List;
import org.newshabit.app.news.domain.model.NewsDetail;
import org.newshabit.app.news.domain.model.NewsSimple;

public interface RefinedNewsUseCase {
	List<NewsSimple> getTodayNews(int userId);
	void deleteThresholdNews(int clickCntThreshold, LocalDate thresholdDay);
	List<NewsSimple> getTrendingNews(int page);
	NewsDetail getNewsDetail(int newsId);
}
