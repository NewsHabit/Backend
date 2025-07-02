package org.newshabit.app.news.application.port.output;

import java.util.List;
import org.newshabit.app.news.domain.model.TodayNews;

public interface TodayNewsPort {
	boolean isTodayNews(Integer newsId);
	List<TodayNews> getTodayNewsByUserId(Integer userId);
}
