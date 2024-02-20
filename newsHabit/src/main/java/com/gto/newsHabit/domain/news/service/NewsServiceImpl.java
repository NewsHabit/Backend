package com.gto.newsHabit.domain.news.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
	 * <p>각 카테고리별 뽑는 개수는 최대한 균등하게 뽑아줍니다.</p>
	 * @param requestDto categories, cnt
	 * @return List<News>
	 */
	@Override
	public List<News> getRecommendedNewsList(RecommendedNewsRequestDto requestDto) {
		// init
		List<NewsCategory> newsCategories = requestDto.getCategories();
		Collections.shuffle(newsCategories);
		int cnt = (int)(requestDto.getCnt() / newsCategories.size());
		int restCnt = (int)(requestDto.getCnt() % newsCategories.size());

		List<News> newsList = newsRepositoryImpl.findAllByCategoryIn(newsCategories);
		HashMap<NewsCategory, List<News>> categoryMap = new HashMap<>();
		HashMap<NewsCategory, Integer> numByCategory = new HashMap<>();
		newsCategories.forEach(category -> categoryMap.put(category, new ArrayList<>()));
		newsList.forEach(news -> categoryMap.get(news.getCategory()).add(news));
		for (int i = 0; i < newsCategories.size(); i++) {
			int choseCnt = i < restCnt ? cnt + 1 : cnt;
			numByCategory.put(newsCategories.get(i), choseCnt);
		}

		List<News> recommendNewsList = new ArrayList<>();
		numByCategory.forEach(((category, targetCnt) -> {
			List<News> categoryNewsList = categoryMap.get(category);
			List<Integer> randomIdx = getRandomIdx(categoryNewsList.size(), targetCnt);
			randomIdx.forEach(idx -> recommendNewsList.add(categoryNewsList.get(idx)));
		}));
		return recommendNewsList;
	}

	/**
	 * <p>리스트의 사이즈 안에서 랜덤한 인덱스 리스트를 반환해준다.</p>
	 * @param listSize 타겟 리스트 사이즈
	 * @param cnt 고르고자하는 인덱스 개수
	 * @return List<Integer>
	 */
	private List<Integer> getRandomIdx(int listSize, int cnt) {
		if (listSize == 0 || cnt > listSize) {
			// 서버 에러 로깅
			return new ArrayList<>();
		}
		Set<Integer> set = new HashSet<>();
		Random random = new Random();
		while (set.size() < cnt) {
			int num = random.nextInt(listSize);
			set.add(num);
		}
		return set.stream().toList();
	}
}
