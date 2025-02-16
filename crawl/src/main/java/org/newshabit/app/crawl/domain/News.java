package org.newshabit.app.crawl.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class News {
	private String title;
	private String content;
	private String originalLink;
	private LocalDateTime crawledTime;
	private Category category;
}
