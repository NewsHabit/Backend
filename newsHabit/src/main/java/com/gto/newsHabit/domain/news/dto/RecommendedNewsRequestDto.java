package com.gto.newsHabit.domain.news.dto;

import java.util.List;

import com.gto.newsHabit.data.type.NewsCategory;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendedNewsRequestDto {
	@NotNull
	@Size(min = 1, max = 6, message = "카테고리 개수는 1 ~ 6개 이어야합니다.")
	private List<NewsCategory> categories;

	@NotNull
	@Min(value = 1, message = "개수는 최소 한개 이상이어야 합니다.")
	@Max(value = 5, message = "개수는 최대 5개 이하여야 합니다.")
	private long cnt;

	RecommendedNewsRequestDto(List<NewsCategory> categories, long cnt) {
		this.categories = categories;
		this.cnt = cnt;
	}
}
