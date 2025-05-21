package org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_daily_goal_log")
public class UserDailyGoalLogEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "daily_goal", nullable = false)
	private int dailyGoal;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@Column(name = "user_id")
	private Integer userId;

	private UserDailyGoalLogEntity(int dailyGoal, LocalDate startDate, LocalDate endDate, int userId) {
		this.id = null;
		this.dailyGoal = dailyGoal;
		this.startDate = startDate;
		this.endDate = endDate;
		this.userId = userId;
	}

	public static UserDailyGoalLogEntity create(int dailyGoal, LocalDate startDate, LocalDate endDate, int userId) {
		return new UserDailyGoalLogEntity(
			dailyGoal,
			startDate,
			endDate,
			userId
		);
	}

	public static UserDailyGoalLogEntity create(int dailyGoal, LocalDate startDate, int userId) {
		return new UserDailyGoalLogEntity(
			dailyGoal,
			startDate,
			null,
			userId
		);
	}
}
