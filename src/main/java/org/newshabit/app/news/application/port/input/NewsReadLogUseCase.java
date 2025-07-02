package org.newshabit.app.news.application.port.input;

public interface NewsReadLogUseCase {
	void updateNewsReadLog(Integer userId, Integer newsId);
}
