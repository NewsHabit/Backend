package org.newshabit.app.crawl.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class News {
	private String title;
	private String content;
}
