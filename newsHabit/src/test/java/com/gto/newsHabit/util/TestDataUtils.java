package com.gto.newsHabit.util;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gto.newsHabit.data.News;
import com.gto.newsHabit.data.type.NewsCategory;
import com.gto.newsHabit.domain.news.data.NewsRepository;
import com.gto.newsHabit.domain.news.service.NewsService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestDataUtils {
	@Autowired NewsService newsService;
	@Autowired NewsRepository newsRepository;

	/**
	 * <p>카테고리와 시간을 기준으로 뉴스를 만들어 준다.</p>
	 * <p>디비 id값인 url 은 현재시간으로 넣어 고유하게 만들어준다.</p>
	 * @param category
	 * @param dateTime
	 */
	public void saveNews(NewsCategory category, LocalDateTime dateTime) {
		News news = News.builder()
			.naverUrl(LocalDateTime.now().toString())
			.title("")
			.category(category)
			.dateTime(dateTime)
			.imgLink("")
			.description("")
			.build();
		newsRepository.save(news);
	}

	/**
	 * <p>카테고리와 시간을 기준으로 뉴스 리스트를 만들어 준다.</p>
	 * @param category
	 * @param dateTime
	 * @param cnt
	 */
	public void saveNewsList(NewsCategory category, LocalDateTime dateTime, int cnt) {
		for (int i = 0; i < cnt; i++) {
			saveNews(category, dateTime);
		}
	}

}
