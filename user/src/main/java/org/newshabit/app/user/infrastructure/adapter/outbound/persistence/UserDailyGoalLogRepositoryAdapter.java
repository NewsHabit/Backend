package org.newshabit.app.user.infrastructure.adapter.outbound.persistence;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.user.application.port.output.UserDailyGoalLogOutputPort;
import org.newshabit.app.user.common.exception.ErrorCode;
import org.newshabit.app.user.common.exception.NotFoundException;
import org.newshabit.app.user.domain.model.UserDailyGoal;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity.UserDailyGoalLogEntity;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity.mapper.EntityMapper;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.repository.UserDailyGoalLogRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDailyGoalLogRepositoryAdapter implements UserDailyGoalLogOutputPort {
	private final UserDailyGoalLogRepository userDailyGoalLogRepository;
	private final EntityMapper entityMapper;

	public UserDailyGoal save(UserDailyGoal userDailyGoal) {
		UserDailyGoalLogEntity userDailyGoalLogEntity = userDailyGoalLogRepository.save(entityMapper.toEntity(userDailyGoal));

		return entityMapper.toDomain(userDailyGoalLogEntity);
	}

	@Override
	public UserDailyGoal findLatestByUserId(int userId) {
		Optional<UserDailyGoalLogEntity> optionalEntity = userDailyGoalLogRepository.findLatestByUserId(userId);

		if (optionalEntity.isEmpty()) {
			throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
		}

		return entityMapper.toDomain(optionalEntity.get());
	}
}
