package com.gto.newsHabit.domain.news.service;

import java.util.List;
import java.util.Set;

import com.gto.newsHabit.data.News;
import com.gto.newsHabit.data.type.NewsCategory;

public interface NewsService {
	List<News> getHotNewsList();
	List<News> getRecommendedNewsList(Set<NewsCategory> params, long cnt);
}
