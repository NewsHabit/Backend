package org.newshabit.app.user.domain.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.newshabit.app.common.domain.enums.NewsCategory;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Register {
	String socialId;
	String username;
	List<NewsCategory> categoryList;
	int dailyGoal;
}
