package org.newshabit.app.news.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.newshabit.app.common.domain.enums.NewsCategory;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TodayNewsDetail {
	private int newsId;
	private String title;
	private NewsCategory category;
	private String description;
}
