package org.newshabit.app.news.application.service;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.news.application.port.input.NewsReadLogUseCase;
import org.newshabit.app.news.application.port.output.NewsReadLogPort;
import org.newshabit.app.news.domain.model.NewsReadLog;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsReadLogService implements NewsReadLogUseCase {
	private final NewsReadLogPort newsReadLogPort;

	@Override
	public void updateNewsReadLog(NewsReadLog newsReadLog) {
		newsReadLogPort.updateNewsReadLog(newsReadLog);
	}
}
