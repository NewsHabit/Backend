package org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity.mapper;

import org.newshabit.app.user.domain.model.User;
import org.newshabit.app.user.domain.model.UserDailyGoal;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity.UserDailyGoalEntity;
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

	public UserDailyGoal toDomain(UserDailyGoalEntity entity) {
		return new UserDailyGoal(
			entity.getId(),
			entity.getDailyGoal(),
			entity.getStartDate(),
			entity.getEndDate(),
			entity.getUser().getId()
		);
	}

	public UserDailyGoalEntity toEntity(UserDailyGoal userDailyGoal) {
		return UserDailyGoalEntity.create(
			userDailyGoal.getId(),
			null,
			userDailyGoal.getDailyGoal(),
			userDailyGoal.getStartDate(),
			userDailyGoal.getEndDate()
		);
	}
}
