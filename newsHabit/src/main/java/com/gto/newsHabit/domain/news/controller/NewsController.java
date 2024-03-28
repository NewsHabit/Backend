package com.gto.newsHabit.domain.news.controller;

import java.util.Set;

import org.springframework.http.ResponseEntity;

import com.gto.newsHabit.data.type.NewsCategory;
import com.gto.newsHabit.domain.news.dto.HotNewsResponseDtoList;
import com.gto.newsHabit.domain.news.dto.RecommendedNewsResponseDtoList;

public interface NewsController {
	ResponseEntity<HotNewsResponseDtoList> getHotNewsList();
	ResponseEntity<RecommendedNewsResponseDtoList> getRecommendedNewsList(Set<NewsCategory> params, long cnt);
}
