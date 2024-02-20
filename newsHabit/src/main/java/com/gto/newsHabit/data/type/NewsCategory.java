package com.gto.newsHabit.data.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NewsCategory {
	POLITICS("POLITICS", "정치"),
	ECONOMY("ECONOMY", "경제"),
	SOCIETY("SOCIETY", "사회"),
	LIFESTYLE_CULTURE("LIFESTYLE/CULTURE", "생활/문화"),
	IT_SCIENCE("IT/SCIENCE", "기술/과학"),
	WORLD("WORLD", "세계"),
	HOT("HOT", "실기간 인기 검색");

	private final String code;
	private final String desc;
}
