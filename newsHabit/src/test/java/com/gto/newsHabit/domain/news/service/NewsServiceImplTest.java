package com.gto.newsHabit.domain.news.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.gto.newsHabit.data.News;
import com.gto.newsHabit.data.type.NewsCategory;
import com.gto.newsHabit.domain.news.data.NewsRepository;
import com.gto.newsHabit.domain.news.exception.InvalidParameterException;
import com.gto.newsHabit.util.annotation.UnitTest;

@UnitTest
class NewsServiceImplTest {
	@Mock
	NewsRepository newsRepository;
	@InjectMocks
	NewsServiceImpl newsService;

	@Nested
	@DisplayName("GetHotNewsList 단위 테스트")
	class GetHotNewsList {
		@Test
		@DisplayName("success")
		void success() {
			// given
			given(newsRepository.findAllByCategory(any())).willReturn(new ArrayList<>());
			// when, then
			newsService.getHotNewsList();
		}
	}

	@Nested
	@DisplayName("GetRecommendedNewsList 단위 테스트")
	@MockitoSettings(strictness = Strictness.LENIENT)
	class GetRecommendedNewsList {
		Set<NewsCategory> categories;
		List<News> newsList;
		long chooseCnt;

		@BeforeEach
		void beforeEach() {
			categories = new HashSet<>(List.of(NewsCategory.WORLD, NewsCategory.IT_SCIENCE, NewsCategory.LIFESTYLE_CULTURE));
			newsList = new ArrayList<>();
			for(int i = 0; i < 5; i++) {
				for (NewsCategory category : categories) {
					if (category.equals(NewsCategory.HOT)) {
						continue;
					}
					newsList.add(new News(category + String.valueOf(i), "", category, LocalDateTime.now(), "", ""));
				}
			}
		}

		@Test
		@DisplayName("success")
		void success() {
			// given
			chooseCnt = 5;
			given(newsRepository.findAllByCategoryIn(anyList())).willReturn(newsList);
			// when, then
			List<News> result = newsService.getRecommendedNewsList(categories, chooseCnt);

			assertThat(result.size()).isEqualTo(chooseCnt);
		}

		@Test
		@DisplayName("BAD_PARAMETERS_cnt")
		void badParameters_cnt() {
			// given
			chooseCnt = 7;
			given(newsRepository.findAllByCategoryIn(anyList())).willReturn(newsList);
			// when, then
			assertThatThrownBy(()->newsService.getRecommendedNewsList(categories, chooseCnt))
				.isInstanceOf(InvalidParameterException.class);
		}

		@Test
		@DisplayName("BAD_PARAMETERS")
		void badParameters_list_size() {
			// given
			chooseCnt = 3;
			categories.addAll(Arrays.asList(NewsCategory.values()));
			given(newsRepository.findAllByCategoryIn(anyList())).willReturn(newsList);
			// when, then
			assertThatThrownBy(()->newsService.getRecommendedNewsList(categories, chooseCnt))
				.isInstanceOf(InvalidParameterException.class);
		}
	}
}
