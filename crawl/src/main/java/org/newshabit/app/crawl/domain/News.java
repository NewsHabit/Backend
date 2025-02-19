package org.newshabit.app.crawl.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class News {
	private final String title;
	private final String content;
	private final String originalLink;
	private final LocalDateTime crawledTime;
	private final Category category;

	private News(String title, String content, String originalLink, LocalDateTime crawledTime, Category category) {
		this.title = title;
		this.content = content;
		this.originalLink = originalLink;
		this. crawledTime = crawledTime;
		this.category = category;
	}

	public static News create(String title, String content, String originalLink, LocalDateTime crawledTime, Category category) {
		return new News(title, content, originalLink, crawledTime, category);
	}
}
