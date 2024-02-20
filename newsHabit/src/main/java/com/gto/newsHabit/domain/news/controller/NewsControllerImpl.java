package com.gto.newsHabit.domain.news.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gto.newsHabit.data.News;
import com.gto.newsHabit.domain.news.dto.HotNewsResponseDto;
import com.gto.newsHabit.domain.news.dto.HotNewsResponseDtoList;
import com.gto.newsHabit.domain.news.dto.RecommendedNewsRequestDto;
import com.gto.newsHabit.domain.news.dto.RecommendedNewsResponseDto;
import com.gto.newsHabit.domain.news.dto.RecommendedNewsResponseDtoList;
import com.gto.newsHabit.domain.news.service.NewsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/news-habit")
public class NewsControllerImpl implements NewsController {
	private final NewsService newsService;
	private final ModelMapper modelMapper;

	/**
	 * <p>현재 시간대의 인기 검색어 기반 기사를 가져온다.</p>
	 * @return HotNewsResponseDtoList
	 */
	@GetMapping("/issue")
	@Override
	public ResponseEntity<HotNewsResponseDtoList> getHotNewsList() {
		List<News> newsList = newsService.getHotNewsList();
		List<HotNewsResponseDto> responseDtoList = newsList.stream()
			.map(news -> modelMapper.map(news, HotNewsResponseDto.class))
			.collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(
			HotNewsResponseDtoList.builder()
				.hotNewsResponseDtoList(responseDtoList)
				.build());
	}

	/**
	 * <p>주어진 requestDto 값에 근거해 추천 기사들을 뽑아준다.</p>
	 * @param requestDto requestDto
	 * @return RecommendedNewsResponseDtoList
	 */
	@GetMapping("/recommendation")
	@Override
	public ResponseEntity<RecommendedNewsResponseDtoList> getRecommendedNewsList(
		@RequestBody @Validated RecommendedNewsRequestDto requestDto) {
		List<News> newsList = newsService.getRecommendedNewsList(requestDto);
		List<RecommendedNewsResponseDto> requestDtoList = newsList.stream()
			.map(news -> modelMapper.map(news, RecommendedNewsResponseDto.class))
			.collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(
			RecommendedNewsResponseDtoList.builder()
				.recommendedNewsResponseDtoList(requestDtoList)
				.build());
	}
}
