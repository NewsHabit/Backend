package org.newshabit.app.news.application.port.input;

import java.util.List;
import org.newshabit.app.news.domain.model.RefinedNews;

public interface RefinedNewsUseCase {
	List<RefinedNews> getTodayNews(int userId);
}
