package org.newshabit.app.user.application.service;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.user.application.port.input.MemberUserCase;
import org.newshabit.app.user.application.port.output.UserDailyGoalLogOutputPort;
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
	private final UserDailyGoalLogOutputPort userDailyGoalLogOutputPort;

	@Override
	public MemberSettings getMemberSettings(int userId) throws NotFoundException {
		User user = userRepositoryOutputPort.findByUserId(userId).orElseThrow(NotFoundException::new);

		UserDailyGoal userDailyGoal = userDailyGoalLogOutputPort.findLatestByUserId(userId);

		return new MemberSettings(
			user.getUsername(),
			user.getInterestCategories(),
			userDailyGoal.getDailyGoal()
		);
	}
}
