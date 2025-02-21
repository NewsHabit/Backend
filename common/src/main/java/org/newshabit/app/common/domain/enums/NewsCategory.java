package org.newshabit.app.common.domain.enums;


import lombok.Getter;

@Getter
public enum NewsCategory {
	POLITICS(100),
	ECONOMY(101),
	SOCIETY(102),
	LIFESTYLE_CULTURE(103),
	WORLD(104),
	IT_SCIENCE(105);

	private final int code;

	NewsCategory(int code) {
		this.code = code;
	}
}
