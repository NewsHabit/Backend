package com.gto.newsHabit.domain.news.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HotNewsResponseDto {
	private String title;
	private String naverUrl;
	private String imgLink;
	private String description;

	@Builder
	HotNewsResponseDto(String title, String naverUrl, String imgLink, String description) {
		this.title = title;
		this.naverUrl = naverUrl;
		this.imgLink = imgLink;
		this.description = description;
	}
}
