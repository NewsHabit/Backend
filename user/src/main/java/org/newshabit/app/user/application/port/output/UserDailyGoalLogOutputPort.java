package org.newshabit.app.user.application.port.output;

import org.newshabit.app.user.domain.model.UserDailyGoal;

public interface UserDailyGoalLogOutputPort {
	UserDailyGoal save(UserDailyGoal userDailyGoal);
	UserDailyGoal findLatestByUserId(int userId);
}
