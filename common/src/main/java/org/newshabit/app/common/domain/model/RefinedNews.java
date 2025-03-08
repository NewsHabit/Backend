package org.newshabit.app.common.domain.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.newshabit.app.common.domain.entity.RefinedNewsEntity;
import org.newshabit.app.common.domain.enums.NewsCategory;

@Getter
@ToString
@AllArgsConstructor
public class RefinedNews {
	private String title;
	private String whoSummary;
	private String whenSummary;
	private String whereSummary;
	private String whatSummary;
	private String whySummary;
	private String howSummary;
	private String keyword;
	private String summary;
	private LocalDateTime crawledTime;
	private NewsCategory newsCategory;
	private Long clickCnt;
	private String originalUrl;

	public static RefinedNewsEntity toRefinedNewsEntity(RefinedNews refinedNews) {
		return new RefinedNewsEntity(
			null,
			refinedNews.title,
			refinedNews.whoSummary,
			refinedNews.whenSummary,
			refinedNews.whereSummary,
			refinedNews.whatSummary,
			refinedNews.whySummary,
			refinedNews.howSummary,
			refinedNews.keyword,
			refinedNews.summary,
			refinedNews.crawledTime,
			refinedNews.newsCategory,
			refinedNews.clickCnt,
			refinedNews.originalUrl
		);
	}
}
