package org.newshabit.app.user.domain.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDailyGoal {
	private Integer id;
	private int dailyGoal;
	private LocalDate startDate;
	private LocalDate endDate;
	private int userId;

	public void updateEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
}
