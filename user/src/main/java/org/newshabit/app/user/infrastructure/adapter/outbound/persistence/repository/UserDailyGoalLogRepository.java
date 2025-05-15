package org.newshabit.app.user.infrastructure.adapter.outbound.persistence.repository;

import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity.UserDailyGoalLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDailyGoalLogRepository extends JpaRepository<UserDailyGoalLogEntity, Long> {
}
