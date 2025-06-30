package org.newshabit.app.news.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.news.application.port.input.RefinedNewsUseCase;
import org.newshabit.app.news.application.port.output.RefinedNewsRepositoryOutputPort;
import org.newshabit.app.news.domain.model.RefinedNews;
import org.newshabit.app.user.application.port.output.UserDailyGoalOutputPort;
import org.newshabit.app.user.domain.model.UserDailyGoal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefinedNewsService implements RefinedNewsUseCase {
	private final RefinedNewsRepositoryOutputPort refinedNewsRepositoryOutputPort;
	private final UserDailyGoalOutputPort userDailyGoalOutputPort;

	@Override
	public List<RefinedNews> getTodayNews(int userId) {
		/**
		 * 오늘의 뉴스 이미 발행 되었는지 확인.
		 * 발행 안되었으면 해당 유저 오늘의 뉴스 구독 수 확인
		 * 해당 개수만큼 오늘의 뉴스 발행
		 */

		List<RefinedNews> todayNews = refinedNewsRepositoryOutputPort.findTodayNewsByUserId(userId);

		if (todayNews.isEmpty()) {
			UserDailyGoal userDailyGoal = userDailyGoalOutputPort.findLatestByUserId(userId);
			int dailyGoal = userDailyGoal.getDailyGoal();


			/**
			 * 뉴스 발행
			 */
		}


		return todayNews;
	}
}
