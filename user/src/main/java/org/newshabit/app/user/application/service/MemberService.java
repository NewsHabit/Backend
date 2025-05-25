package org.newshabit.app.user.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.common.domain.enums.NewsCategory;
import org.newshabit.app.user.application.port.input.MemberUserCase;
import org.newshabit.app.user.application.port.output.UserDailyGoalOutputPort;
import org.newshabit.app.user.application.port.output.UserRepositoryOutputPort;
import org.newshabit.app.user.domain.model.MemberSettings;
import org.newshabit.app.user.domain.model.User;
import org.newshabit.app.user.domain.model.UserDailyGoal;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberUserCase {
	private final UserRepositoryOutputPort userRepositoryOutputPort;
	private final UserDailyGoalOutputPort userDailyGoalOutputPort;

	@Override
	public MemberSettings getMemberSettings(int userId) throws NotFoundException {
		User user = userRepositoryOutputPort.findByUserId(userId).orElseThrow(NotFoundException::new);

		UserDailyGoal userDailyGoal = userDailyGoalOutputPort.findLatestByUserId(userId);

		return new MemberSettings(
			user.getUsername(),
			user.getInterestCategories(),
			userDailyGoal.getDailyGoal()
		);
	}

	@Override
	public void updateUsername(int userId, String username) throws NotFoundException {
		User user = userRepositoryOutputPort.findByUserId(userId).orElseThrow(NotFoundException::new);

		userRepositoryOutputPort.updateUsername(user, username);
	}

	@Override
	public void updateInterestCategories(int userId, List<NewsCategory> interestCategories) throws NotFoundException {
		User user = userRepositoryOutputPort.findByUserId(userId).orElseThrow(NotFoundException::new);

		userRepositoryOutputPort.updateInterestCategories(user, interestCategories);
	}
}
