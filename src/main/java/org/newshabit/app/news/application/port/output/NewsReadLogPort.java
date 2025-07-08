package org.newshabit.app.news.application.port.output;

import org.newshabit.app.news.domain.model.NewsReadLog;

import java.time.LocalDate;
import java.util.List;

public interface NewsReadLogPort {
	void updateNewsReadLog(NewsReadLog newsReadLog);
	List<NewsReadLog> findByUserIdAndDateRange(int userId, LocalDate startDate, LocalDate endDate);
}
