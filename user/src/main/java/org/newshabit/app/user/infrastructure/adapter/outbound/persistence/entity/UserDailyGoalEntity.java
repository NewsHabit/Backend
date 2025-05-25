package org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_daily_goal_log")
public class UserDailyGoalEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@Setter
	private UserEntity user;

	@Column(name = "daily_goal", nullable = false)
	private int dailyGoal;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	private UserDailyGoalEntity(Integer id,UserEntity user, int dailyGoal, LocalDate startDate, LocalDate endDate) {
		this.id = id;
		this.user = user;
		this.dailyGoal = dailyGoal;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public static UserDailyGoalEntity create(Integer id, UserEntity user, int dailyGoal, LocalDate startDate, LocalDate endDate) {
		return new UserDailyGoalEntity(
			id,
			user,
			dailyGoal,
			startDate,
			endDate
		);
	}

	public static UserDailyGoalEntity create(Integer id, UserEntity user, int dailyGoal, LocalDate startDate) {
		return new UserDailyGoalEntity(
			id,
			user,
			dailyGoal,
			startDate,
			null
		);
	}
}
