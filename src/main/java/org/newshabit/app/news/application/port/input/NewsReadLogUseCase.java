package org.newshabit.app.news.application.port.input;

import org.newshabit.app.news.domain.model.NewsReadLog;

public interface NewsReadLogUseCase {
	void updateNewsReadLog(NewsReadLog newsReadLog);
}
