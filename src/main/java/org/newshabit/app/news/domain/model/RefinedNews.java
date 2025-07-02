package org.newshabit.app.news.domain.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.newshabit.app.common.domain.enums.NewsCategory;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefinedNews {
	private Integer id;
	private String title;
	private String whoSummary;
	private String whenSummary;
	private String whereSummary;
	private String whatSummary;
	private String whySummary;
	private String howSummary;
	private String keyword;
	private String summary;
	private LocalDateTime publishedAt;
	private NewsCategory newsCategory;
	private Integer clickCnt;
	private String originalUrl;

	public void updateClickCnt() {
		this.clickCnt++;
	}
}
