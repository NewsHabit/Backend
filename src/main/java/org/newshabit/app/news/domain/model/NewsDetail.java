package org.newshabit.app.news.domain.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.newshabit.app.common.domain.enums.NewsCategory;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewsDetail {
	private Integer id;
	private String title;
	private String whoSummary;
	private String whenSummary;
	private String whereSummary;
	private String whatSummary;
	private String whySummary;
	private String howSummary;
	private String keyword;
	private LocalDateTime publishedAt;
	private NewsCategory newsCategory;
}
