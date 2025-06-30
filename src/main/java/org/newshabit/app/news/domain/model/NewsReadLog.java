package org.newshabit.app.news.domain.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.newshabit.app.common.domain.enums.NewsCategory;

@Getter
@AllArgsConstructor
public class NewsReadLog {
	private Integer userId;
	private Integer newsId;
	private NewsCategory category;
	private boolean isTodayNews;
	private LocalDateTime publishedAt;
}
