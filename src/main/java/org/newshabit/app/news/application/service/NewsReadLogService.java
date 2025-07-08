package org.newshabit.app.news.application.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.common.domain.enums.NewsCategory;
import org.newshabit.app.news.application.port.input.NewsReadLogUseCase;
import org.newshabit.app.news.application.port.output.NewsReadLogPort;
import org.newshabit.app.news.application.port.output.RefinedNewsPort;
import org.newshabit.app.news.application.port.output.TodayNewsPort;
import org.newshabit.app.news.domain.model.NewsReadLog;
import org.newshabit.app.news.domain.model.RefinedNews;
import org.newshabit.app.news.domain.model.TodayNewsReadLog;
import org.newshabit.app.user.application.port.output.UserDailyGoalOutputPort;
import org.newshabit.app.user.domain.model.UserDailyGoal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsReadLogService implements NewsReadLogUseCase {
	private final NewsReadLogPort newsReadLogPort;
	private final RefinedNewsPort refinedNewsPort;
	private final TodayNewsPort todayNewsPort;

	private final UserDailyGoalOutputPort userDailyGoalOutputPort;

	@Override
	public void updateNewsReadLog(Integer userId, Integer newsId) {
		RefinedNews refinedNews = refinedNewsPort.findById(newsId);

		refinedNews.updateClickCnt();

		refinedNewsPort.save(refinedNews);

		if (userId != null) {
			NewsCategory category = refinedNews.getNewsCategory();

			boolean isTodayNews = todayNewsPort.isTodayNews(userId, newsId);

			NewsReadLog newsReadLog = new NewsReadLog(
				null,
				userId,
				newsId,
				category,
				isTodayNews,
				LocalDate.now()
			);

			newsReadLogPort.updateNewsReadLog(newsReadLog);
		}
	}

	@Override
	public List<TodayNewsReadLog> getNewsReadRecords(int userId, int year, int month) {
		YearMonth ym = YearMonth.of(year, month);

		LocalDate startDate = ym.atDay(1);
		LocalDate endDate   = ym.atEndOfMonth();

		List<UserDailyGoal> userDailyGoals = userDailyGoalOutputPort.findByUserIdAndDateRange(userId, startDate, endDate);
		List<NewsReadLog> newsReadLogs = newsReadLogPort.findByUserIdAndDateRange(userId, startDate, endDate);



		return List.of();
	}
}
