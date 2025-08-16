package org.newshabit.app.news.application.service;

import java.time.LocalDate;
import java.util.List;

import jakarta.transaction.Transactional;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.common.domain.enums.NewsCategory;
import org.newshabit.app.news.application.port.input.RefinedNewsUseCase;
import org.newshabit.app.news.application.port.output.NewsReadLogPort;
import org.newshabit.app.news.application.port.output.RefinedNewsPort;
import org.newshabit.app.news.application.port.output.TodayNewsPort;
import org.newshabit.app.news.domain.model.NewsDetail;
import org.newshabit.app.news.domain.model.RefinedNews;
import org.newshabit.app.news.domain.model.TodayNews;
import org.newshabit.app.news.domain.model.NewsSimple;
import org.newshabit.app.user.application.port.output.UserDailyGoalOutputPort;
import org.newshabit.app.user.application.port.output.UserRepositoryOutputPort;
import org.newshabit.app.user.domain.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefinedNewsService implements RefinedNewsUseCase {
	private final TodayNewsPort todayNewsPort;
	private final RefinedNewsPort refinedNewsPort;

	private final UserDailyGoalOutputPort userDailyGoalOutputPort;
	private final UserRepositoryOutputPort userRepositoryOutputPort;
	private final NewsReadLogPort newsReadLogPort;

	@Override
	@Transactional
	public List<NewsSimple> getTodayNews(int userId) {
		List<TodayNews> todayNewsList = todayNewsPort.getTodayNewsList(userId);

		if (todayNewsList.isEmpty()) {
			int dailyGoal = userDailyGoalOutputPort.findLatestByUserId(userId).getDailyGoal();

			User user = userRepositoryOutputPort.findByUserId(userId).orElseThrow(
				() -> new IllegalArgumentException("User not found with userId: " + userId)
			);

			todayNewsList = selectTodayNews(userId, dailyGoal, user.getInterestCategories());

			todayNewsPort.saveTodayNewsList(todayNewsList);
		}

		List<Integer> newsIds = todayNewsList.stream()
			.map(TodayNews::getNewsId)
			.toList();

		List<RefinedNews> refinedNewsList = refinedNewsPort.findAllByNewsIds(newsIds);

		return refinedNewsList.stream()
			.map(refinedNews -> new NewsSimple(
			refinedNews.getId(),
			refinedNews.getTitle(),
			refinedNews.getNewsCategory(),
			refinedNews.getSummary(),
			newsReadLogPort.isRead(userId, refinedNews.getId())
		))
		.toList();
	}

	private List<TodayNews> selectTodayNews(int userId, int dailyGoal, List<NewsCategory> interestCategories) {
		int categoryCount = interestCategories.size();
		int baseCount = dailyGoal / categoryCount;
		int remainder = dailyGoal % categoryCount;

		Map<NewsCategory, Integer> categoryCountMap = interestCategories.stream()
			.collect(Collectors.toMap(
				category -> category,
				category -> baseCount
			));

		for (int i = 0; i < remainder; i++) {
			NewsCategory category = interestCategories.get(i);
			categoryCountMap.put(category, categoryCountMap.get(category) + 1);
		}

		return interestCategories.stream().flatMap(
			category -> {
				int size = categoryCountMap.get(category);

				return  refinedNewsPort.findTodayNewsCandidates(userId, category, size).stream().map(
				refinedNews -> new TodayNews(
					null,
					refinedNews.getId(),
					userId,
					LocalDate.now()
				));
			}
		).toList();
	}

	@Override
	@Transactional
	public void deleteThresholdNews(int clickCntThreshold, LocalDate thresholdDay) {
		refinedNewsPort.deleteThresholdNews(clickCntThreshold, thresholdDay);
		refinedNewsPort.updateClickCntAfterDeletion();
	}

	@Override
	public List<NewsSimple> getTrendingNews(int page) {
		return refinedNewsPort.getTrendingNews(page).stream().map(
			refinedNews -> new NewsSimple(
				refinedNews.getId(),
				refinedNews.getTitle(),
				refinedNews.getNewsCategory(),
				refinedNews.getSummary(),
				false
			)
		).toList();
	}

	@Override
	public NewsDetail getNewsDetail(int newsId) {
		RefinedNews refinedNews = refinedNewsPort.findById(newsId);

		return new NewsDetail(
			refinedNews.getId(),
			refinedNews.getTitle(),
			refinedNews.getWhoSummary(),
			refinedNews.getWhenSummary(),
			refinedNews.getWhereSummary(),
			refinedNews.getWhatSummary(),
			refinedNews.getWhySummary(),
			refinedNews.getHowSummary(),
			refinedNews.getKeyword(),
			refinedNews.getPublishedAt(),
			refinedNews.getNewsCategory()
		);
	}
}
