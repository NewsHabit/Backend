package org.newshabit.app.user.application.port.output;

import org.newshabit.app.user.domain.model.UserDailyGoal;

public interface UserDailyGoalOutputPort {
	UserDailyGoal save(UserDailyGoal userDailyGoal);
	UserDailyGoal findLatestByUserId(int userId);
}
