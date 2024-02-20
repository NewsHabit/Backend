package com.gto.newsHabit.domain.news.controller;

import org.springframework.http.ResponseEntity;

import com.gto.newsHabit.domain.news.dto.HotNewsResponseDtoList;
import com.gto.newsHabit.domain.news.dto.RecommendedNewsRequestDto;
import com.gto.newsHabit.domain.news.dto.RecommendedNewsResponseDtoList;

public interface NewsController {
	ResponseEntity<HotNewsResponseDtoList> getHotNewsList();
	ResponseEntity<RecommendedNewsResponseDtoList> getRecommendedNewsList(RecommendedNewsRequestDto requestDto);
}
