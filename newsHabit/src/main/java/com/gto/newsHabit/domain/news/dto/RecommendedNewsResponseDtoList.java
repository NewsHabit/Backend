package com.gto.newsHabit.domain.news.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendedNewsResponseDtoList {
	private List<RecommendedNewsResponseDto> recommendedNewsResponseDtoList;

	@Builder
	RecommendedNewsResponseDtoList(List<RecommendedNewsResponseDto> recommendedNewsResponseDtoList) {
		this.recommendedNewsResponseDtoList = recommendedNewsResponseDtoList;
	}
}
