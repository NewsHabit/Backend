package org.newshabit.app.common.domain.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;
import org.newshabit.app.common.domain.enums.NewsCategory;

@Getter
@ToString
public class CrawledNews {
	private final String title;
	private final String content;
	private final String originalLink;
	private final LocalDateTime crawledTime;
	private final NewsCategory newsCategory;

	private CrawledNews(String title, String content, String originalLink, LocalDateTime crawledTime, NewsCategory newsCategory) {
		this.title = title;
		this.content = content;
		this.originalLink = originalLink;
		this.crawledTime = crawledTime;
		this.newsCategory = newsCategory;
	}

	public static CrawledNews create(String title, String content, String originalLink, LocalDateTime crawledTime, NewsCategory newsCategory) {
		return new CrawledNews(title, content, originalLink, crawledTime, newsCategory);
	}
}
