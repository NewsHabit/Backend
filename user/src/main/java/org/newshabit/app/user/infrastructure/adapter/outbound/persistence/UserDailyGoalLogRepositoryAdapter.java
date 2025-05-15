package org.newshabit.app.user.infrastructure.adapter.outbound.persistence;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.user.application.port.output.UserDailyGoalLogOutputPort;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity.UserDailyGoalLogEntity;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.repository.UserDailyGoalLogRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDailyGoalLogRepositoryAdapter implements UserDailyGoalLogOutputPort {
	private final UserDailyGoalLogRepository userDailyGoalLogRepository;

	public UserDailyGoalLogEntity save(UserDailyGoalLogEntity userDailyGoalLog) {
		return userDailyGoalLogRepository.save(userDailyGoalLog);
	}
}
