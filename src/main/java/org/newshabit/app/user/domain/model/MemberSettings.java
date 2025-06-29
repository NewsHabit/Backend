package org.newshabit.app.user.domain.model;

import java.util.List;
import org.newshabit.app.common.domain.enums.NewsCategory;

public record MemberSettings(
	String name,
	List<NewsCategory> categoryList,
	int dailyGoal
) {}
