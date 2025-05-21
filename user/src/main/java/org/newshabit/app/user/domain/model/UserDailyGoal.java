package org.newshabit.app.user.domain.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDailyGoal {
	private int dailyGoal;
	private LocalDate startDate;
	private LocalDate endDate;
	private int userId;
}
