package org.newshabit.app.news.application.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
import org.newshabit.app.user.application.port.output.UserRepositoryOutputPort;
import org.newshabit.app.user.common.exception.ErrorCode;
import org.newshabit.app.user.common.exception.NotFoundException;
import org.newshabit.app.user.domain.model.UserDailyGoal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsReadLogService implements NewsReadLogUseCase {
	private final NewsReadLogPort newsReadLogPort;
	private final RefinedNewsPort refinedNewsPort;
	private final TodayNewsPort todayNewsPort;

	private final UserRepositoryOutputPort userRepositoryOutputPort;
	private final UserDailyGoalOutputPort userDailyGoalOutputPort;

	@Override
	public void updateNewsReadLog(Integer userId, Integer newsId) {
		RefinedNews refinedNews = refinedNewsPort.findById(newsId);

		refinedNews.updateClickCnt();

		refinedNewsPort.save(refinedNews);

		if (userId != null) {
			userRepositoryOutputPort.findByUserId(userId).orElseThrow(
				() -> new NotFoundException(ErrorCode.USER_NOT_FOUND)
			);

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
		userRepositoryOutputPort.findByUserId(userId).orElseThrow(
			() -> new NotFoundException(ErrorCode.USER_NOT_FOUND)
		);

		YearMonth ym = YearMonth.of(year, month);

		LocalDate startDate = ym.atDay(1);
		LocalDate endDate   = ym.atEndOfMonth();

		List<UserDailyGoal> userDailyGoals = userDailyGoalOutputPort.findByUserIdAndDateRange(userId, startDate, endDate);
		List<NewsReadLog> newsReadLogs = newsReadLogPort.findByUserIdAndDateRange(userId, startDate, endDate);

		Map<LocalDate, Long> readCountByDate = newsReadLogs.stream()
			.collect(Collectors.groupingBy(
				NewsReadLog::getPublishedAt,
				Collectors.counting()
			));

		List<LocalDate> allDates = Stream.iterate(startDate, date -> date.plusDays(1))
			.limit(ChronoUnit.DAYS.between(startDate, endDate) + 1)
			.toList();

		List<TodayNewsReadLog> result = new ArrayList<>();

		for (LocalDate date : allDates) {
			int goalCount = userDailyGoals.stream()
				.filter(goal ->
					!goal.getStartDate().isAfter(date) &&
					(goal.getEndDate() == null || !goal.getEndDate().isBefore(date))
				)
				.mapToInt(UserDailyGoal::getDailyGoal)
				.findFirst()
				.orElse(0);

			long readCount = readCountByDate.getOrDefault(date, 0L);

			boolean isSatisfied = readCount >= goalCount;

			result.add(new TodayNewsReadLog(date, isSatisfied));
		}

		return result;
	}

	@Override
	public long getTodayNewsTotalClearCnt(int userId) {
		userRepositoryOutputPort.findByUserId(userId).orElseThrow(
			() -> new NotFoundException(ErrorCode.USER_NOT_FOUND)
		);

		Deque<UserDailyGoal> userDailyGoals = new ArrayDeque<>(userDailyGoalOutputPort.findByUserId(userId));
		List<NewsReadLog> newsReadLogs = newsReadLogPort.findByUserId(userId);

		Map<LocalDate, Long> todayNewsClearCntMap = newsReadLogs.stream()
			.filter(NewsReadLog::isTodayNews)
			.map(NewsReadLog::getPublishedAt)
			.collect(Collectors.groupingBy(
				date -> date,
				Collectors.counting())
			);

		long totalClearCnt = 0;

		for (Entry<LocalDate, Long> entry : todayNewsClearCntMap.entrySet()) {
			LocalDate date = entry.getKey();
			Long readCount = entry.getValue();

			UserDailyGoal userDailyGoal = userDailyGoals.peek();

			int targetReadCount = Objects.requireNonNull(
				userDailyGoal, ErrorCode.DAILY_GOAL_ERROR.getMessage()
			).getDailyGoal();

			if (userDailyGoal.getEndDate() != null && date.isAfter(userDailyGoal.getEndDate())) {
				userDailyGoals.pop();
				targetReadCount = Objects.requireNonNull(
					userDailyGoals.peek(), ErrorCode.DAILY_GOAL_ERROR.getMessage()
				).getDailyGoal();
			}

			if (readCount >= targetReadCount) {
				totalClearCnt++;
			}
		}

		return totalClearCnt;
	}
}
