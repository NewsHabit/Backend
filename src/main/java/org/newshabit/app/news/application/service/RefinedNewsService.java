package org.newshabit.app.news.application.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.transaction.Transactional;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.common.domain.enums.NewsCategory;
import org.newshabit.app.news.application.port.input.RefinedNewsUseCase;
import org.newshabit.app.news.application.port.output.RefinedNewsPort;
import org.newshabit.app.news.application.port.output.TodayNewsPort;
import org.newshabit.app.news.domain.model.RefinedNews;
import org.newshabit.app.news.domain.model.TodayNews;
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

	@Override
	@Transactional
	public List<RefinedNews> getTodayNews(int userId) {
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

		return refinedNewsPort.findAllByNewsIds(newsIds);
	}

	private List<TodayNews> selectTodayNews(int userId, int dailyGoal, List<NewsCategory> interestCategories) {
		List<RefinedNews> todayNewsCandidates = refinedNewsPort.findTodayNewsCandidates(userId, interestCategories);

		Collections.shuffle(todayNewsCandidates);

		Map<NewsCategory, List<RefinedNews>> groupedCandidates = todayNewsCandidates.stream()
			.collect(Collectors.groupingBy(RefinedNews::getNewsCategory, Collectors.toList()));

		int categoryCount = interestCategories.size();
		int baseQuota     = dailyGoal / categoryCount;
		int remainder     = dailyGoal % categoryCount;

		List<RefinedNews> picked = new ArrayList<>();

		for (NewsCategory category : interestCategories) {
			List<RefinedNews> bucket = groupedCandidates.getOrDefault(category, Collections.emptyList());

			int quota = baseQuota + (remainder > 0 ? 1 : 0);

			if (bucket.size() >= quota) {
				remainder--;
			}

			int pickCount = Math.min(quota, bucket.size());

			picked.addAll(bucket.subList(0, pickCount));
		}

		return picked.stream().map(
			refinedNews -> new TodayNews(
				null,
				refinedNews.getId(),
				userId,
				LocalDate.now()
			)
		).toList();
	}

	@Override
	@Transactional
	public void deleteThresholdNews(int clickCntThreshold, LocalDate thresholdDay) {
		refinedNewsPort.deleteThresholdNews(clickCntThreshold, thresholdDay);
		refinedNewsPort.updateClickCntAfterDeletion();
	}

	@Override
	public List<RefinedNews> getTrendingNews(int page) {
		return refinedNewsPort.getTrendingNews(page);
	}
}
