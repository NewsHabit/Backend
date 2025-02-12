package org.newshabit.app.crawl.domain;


import lombok.Getter;

@Getter
public enum Category {
	POLITICS(100),
	ECONOMY(101),
	SOCIETY(102),
	LIFESTYLE_CULTURE(103),
	WORLD(104),
	IT_SCIENCE(105);

	private final int code;

	Category(int code) {
		this.code = code;
	}
}
