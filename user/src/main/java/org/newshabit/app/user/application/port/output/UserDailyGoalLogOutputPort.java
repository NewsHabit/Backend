package org.newshabit.app.user.application.port.output;

import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity.UserDailyGoalLogEntity;

public interface UserDailyGoalLogOutputPort {
	UserDailyGoalLogEntity save(UserDailyGoalLogEntity userDailyGoalLog);
}
