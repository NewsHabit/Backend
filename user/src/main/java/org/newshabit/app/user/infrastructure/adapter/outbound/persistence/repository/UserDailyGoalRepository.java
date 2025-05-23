package org.newshabit.app.user.infrastructure.adapter.outbound.persistence.repository;

import java.util.List;
import java.util.Optional;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity.UserDailyGoalEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDailyGoalRepository extends JpaRepository<UserDailyGoalEntity, Long> {

	@Query("""
		SELECT dailyGoal FROM UserDailyGoalEntity dailyGoal
		WHERE dailyGoal.user.id = :userId
		ORDER BY dailyGoal.startDate DESC
		""")
	List<UserDailyGoalEntity> findByUserId(@Param("userId") Integer userId, Pageable pageable);


	default Optional<UserDailyGoalEntity> findLatestByUserId(@Param("userId") Integer userId) {
		List<UserDailyGoalEntity> list =
			findByUserId(userId, PageRequest.of(0, 1));
		return list.stream().findFirst();
	}

}
