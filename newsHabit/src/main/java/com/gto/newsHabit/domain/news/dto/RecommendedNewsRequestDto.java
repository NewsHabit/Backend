package com.gto.newsHabit.domain.news.dto;

import java.util.List;

import com.gto.newsHabit.data.type.NewsCategory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendedNewsRequestDto {
	private List<NewsCategory> categories;
	private long cnt;

	RecommendedNewsRequestDto(List<NewsCategory> categories, long cnt) {
		this.categories = categories;
		this.cnt = cnt;
	}
}
