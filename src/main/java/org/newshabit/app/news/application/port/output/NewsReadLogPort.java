package org.newshabit.app.news.application.port.output;

import org.newshabit.app.news.domain.model.NewsReadLog;

public interface NewsReadLogPort {
	void updateNewsReadLog(NewsReadLog newsReadLog);
}
