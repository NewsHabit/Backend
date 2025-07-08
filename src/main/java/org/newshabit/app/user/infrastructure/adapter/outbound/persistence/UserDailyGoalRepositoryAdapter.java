package org.newshabit.app.user.infrastructure.adapter.outbound.persistence;

import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.user.application.port.output.UserDailyGoalOutputPort;
import org.newshabit.app.user.common.exception.NotFoundException;
import org.newshabit.app.user.domain.model.UserDailyGoal;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity.UserDailyGoalEntity;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity.UserEntity;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity.mapper.EntityMapper;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.repository.UserDailyGoalRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDailyGoalRepositoryAdapter implements UserDailyGoalOutputPort {
	private final UserDailyGoalRepository userDailyGoalRepository;
	private final EntityMapper entityMapper;
	private final EntityManager entityManager;

	public UserDailyGoal save(UserDailyGoal userDailyGoal) {
		UserDailyGoalEntity userDailyGoalEntity = entityMapper.toEntity(userDailyGoal);

		UserEntity userRef = entityManager.getReference(UserEntity.class, userDailyGoal.getUserId());

		userDailyGoalEntity.setUser(userRef);

		UserDailyGoalEntity saved = userDailyGoalRepository.save(userDailyGoalEntity);

		return entityMapper.toDomain(saved);
	}

	@Override
	public UserDailyGoal findLatestByUserId(int userId) throws NotFoundException {
		Optional<UserDailyGoalEntity> optionalEntity = userDailyGoalRepository.findLatestByUserId(userId);

		if (optionalEntity.isEmpty()) {
			throw new NotFoundException();
		}

		return entityMapper.toDomain(optionalEntity.get());
	}

	@Override
	public void deleteByUserId(int userId) {
                userDailyGoalRepository.deleteAllByUserId(userId);
        }

	@Override
	public List<UserDailyGoal> findByUserIdAndDateRange(int userId, LocalDate startDate, LocalDate endDate) {
		return userDailyGoalRepository.findOverlappingDailyGoal(userId, startDate, endDate).stream()
				.map(entityMapper::toDomain)
				.toList();
	}
}
