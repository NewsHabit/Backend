package org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity.mapper;

import org.newshabit.app.user.domain.model.User;
import org.newshabit.app.user.domain.model.UserDailyGoal;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity.UserDailyGoalLogEntity;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {
	public User toDomain(UserEntity entity) {
		return new User(
			entity.getId(),
			entity.getUsername(),
			entity.getUsernameModifiedAt(),
			entity.getInterestCategories(),
			entity.getSocialId(),
			entity.getRole()
		);
	}

	public UserEntity toEntity(User user) {
		return new UserEntity(
			user.getUsername(),
			user.getUsernameModifiedAt(),
			user.getInterestCategories(),
			user.getSocialId(),
			user.getRole()
		);
	}

	public UserDailyGoal toDomain(UserDailyGoalLogEntity entity) {
		return new UserDailyGoal(
			entity.getDailyGoal(),
			entity.getStartDate(),
			entity.getEndDate(),
			entity.getUserId()
		);
	}

	public UserDailyGoalLogEntity toEntity(UserDailyGoal userDailyGoal) {
		return UserDailyGoalLogEntity.create(
			userDailyGoal.getDailyGoal(),
			userDailyGoal.getStartDate(),
			userDailyGoal.getEndDate(),
			userDailyGoal.getUserId()
		);
	}
}
