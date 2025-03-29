package org.newshabit.app.user.application.port;

import org.newshabit.app.user.domain.entity.UserDailyGoalLogEntity;

public interface UserDailyGoalLogOutputPort {
	UserDailyGoalLogEntity save(UserDailyGoalLogEntity userDailyGoalLog);
}
