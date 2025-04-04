package org.newshabit.app.user.domain.dto;

import java.util.List;
import org.newshabit.app.common.domain.enums.NewsCategory;

public record RegisterRequest(
	String socialId,
	String username,
	List<NewsCategory> categoryList,
	int dailyGoal
) {}
