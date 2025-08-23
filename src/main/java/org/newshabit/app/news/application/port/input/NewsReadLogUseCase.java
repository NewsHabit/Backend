package org.newshabit.app.news.application.port.input;

import org.newshabit.app.news.domain.model.TodayNewsReadLog;

import java.util.List;

public interface NewsReadLogUseCase {
	void updateNewsReadLog(Integer userId, Integer newsId);
	List<TodayNewsReadLog>  getNewsReadRecords(int userId, int year, int month);
	long getTodayNewsTotalClearCnt(int userId);
}
