package org.newshabit.app.user.infrastructure.adapter.outbound.persistence.repository;

import java.util.List;
import java.util.Optional;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity.UserDailyGoalLogEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDailyGoalLogRepository extends JpaRepository<UserDailyGoalLogEntity, Long> {

	@Query("SELECT u FROM UserDailyGoalLogEntity u " +
		"WHERE u.userId = :userId " +
		"ORDER BY u.startDate DESC " +
		"LIMIT 1")
	List<UserDailyGoalLogEntity> findByUserId(@Param("userId") Integer userId, Pageable pageable);


	default Optional<UserDailyGoalLogEntity> findLatestByUserId(@Param("userId") Integer userId) {
		List<UserDailyGoalLogEntity> list =
			findByUserId(userId, PageRequest.of(0, 1));
		return list.stream().findFirst();
	}

}
