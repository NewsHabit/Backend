package org.newshabit.app.user.application.port.output;

import org.newshabit.app.user.domain.model.UserDailyGoal;

import java.time.LocalDate;
import java.util.List;

public interface UserDailyGoalOutputPort {
        UserDailyGoal save(UserDailyGoal userDailyGoal);
        UserDailyGoal findLatestByUserId(int userId);

        /**
         * 사용자와 연결된 일일 목표 기록을 삭제합니다.
         * @param userId 사용자 ID
         */
        List<UserDailyGoal> findByUserIdAndDateRange(int userId, LocalDate startDate, LocalDate endDate);
        List<UserDailyGoal> findByUserId(int userId);
}
