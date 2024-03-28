package com.gto.newsHabit.domain.news.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendedNewsResponseDto {
	private String title;
	private String category;
	private String naverUrl;
	private String imgLink;
	private String description;

	@Builder
	RecommendedNewsResponseDto(String title, String category, String naverUrl, String imgLink, String description) {
		this.title = title;
		this.category = category;
		this.naverUrl = naverUrl;
		this.imgLink = imgLink;
		this.description = description;
	}
}
