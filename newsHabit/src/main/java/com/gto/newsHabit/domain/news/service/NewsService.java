package com.gto.newsHabit.domain.news.service;

import java.util.List;

import com.gto.newsHabit.data.News;
import com.gto.newsHabit.domain.news.dto.RecommendedNewsRequestDto;

public interface NewsService {
	List<News> getHotNewsList();
	List<News> getRecommendedNewsList(RecommendedNewsRequestDto requestDto);
}
