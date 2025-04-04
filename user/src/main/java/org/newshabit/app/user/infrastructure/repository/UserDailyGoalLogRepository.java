package org.newshabit.app.user.infrastructure.repository;

import org.newshabit.app.user.domain.entity.UserDailyGoalLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDailyGoalLogRepository extends JpaRepository<UserDailyGoalLogEntity, Long> {
}
