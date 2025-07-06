package org.newshabit.app.news.application.port.output;

import java.util.List;
import org.newshabit.app.news.domain.model.TodayNews;

public interface TodayNewsPort {
	boolean isTodayNews(Integer userId, Integer newsId);
	List<TodayNews> getTodayNewsList(Integer userId);
	void saveTodayNewsList(List<TodayNews> todayNewsList);
}
