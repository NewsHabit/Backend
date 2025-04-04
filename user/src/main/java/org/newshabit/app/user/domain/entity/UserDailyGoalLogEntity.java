package org.newshabit.app.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "user_daily_goal_log")
public class UserDailyGoalLogEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "daily_goal", nullable = false)
	private int dailyGoal;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	private UserDailyGoalLogEntity(int dailyGoal, LocalDate startDate, LocalDate endDate, UserEntity user) {
		this.id = null;
		this.dailyGoal = dailyGoal;
		this.startDate = startDate;
		this.endDate = endDate;
		this.user = user;
	}

	public static UserDailyGoalLogEntity create(int dailyGoal, LocalDate startDate, LocalDate endDate, UserEntity user) {
		return new UserDailyGoalLogEntity(
			dailyGoal,
			startDate,
			endDate,
			user
		);
	}

	public static UserDailyGoalLogEntity create(int dailyGoal, LocalDate startDate, UserEntity user) {
		return new UserDailyGoalLogEntity(
			dailyGoal,
			startDate,
			null,
			user
		);
	}
}
