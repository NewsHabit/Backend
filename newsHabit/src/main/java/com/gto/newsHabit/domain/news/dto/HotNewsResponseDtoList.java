package com.gto.newsHabit.domain.news.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HotNewsResponseDtoList {
	private List<HotNewsResponseDto> hotNewsResponseDtoList;

	@Builder
	HotNewsResponseDtoList(List<HotNewsResponseDto> hotNewsResponseDtoList) {
		this.hotNewsResponseDtoList = hotNewsResponseDtoList;
	}
}
