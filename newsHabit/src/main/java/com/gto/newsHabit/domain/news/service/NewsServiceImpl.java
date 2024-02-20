package com.gto.newsHabit.domain.news.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gto.newsHabit.data.News;
import com.gto.newsHabit.data.type.NewsCategory;
import com.gto.newsHabit.domain.news.data.NewsRepositoryImpl;
import com.gto.newsHabit.domain.news.dto.RecommendedNewsRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
	private final NewsRepositoryImpl newsRepositoryImpl;

	/**
	 * <p>실시간 인기 검색 뉴스를 반환합니다.</p>
	 * @return List<News>
	 */
	@Override
	public List<News> getHotNewsList() {
		return newsRepositoryImpl.findAllByCategory(NewsCategory.HOT);
	}

	/**
	 * <p>categories 의 기사를 총 cnt 개 만큼 랜덤하게 골라서 반환해 줍니다.</p>
	 * @param requestDto categories, cnt
	 * @return List<News>
	 */
	@Override
	public List<News> getRecommendedNewsList(RecommendedNewsRequestDto requestDto) {
		List<News> newsList = newsRepositoryImpl.findAllByCategoryIn(requestDto.getCategories());
		/*
			IMPL
		 */
		return null;
	}
}
