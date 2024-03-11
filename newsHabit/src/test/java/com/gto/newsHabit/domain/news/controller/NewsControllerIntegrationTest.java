package com.gto.newsHabit.domain.news.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gto.newsHabit.data.type.NewsCategory;
import com.gto.newsHabit.domain.news.data.NewsRepository;
import com.gto.newsHabit.domain.news.dto.HotNewsResponseDtoList;
import com.gto.newsHabit.domain.news.dto.RecommendedNewsResponseDtoList;
import com.gto.newsHabit.util.TestDataUtils;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@Transactional
class NewsControllerIntegrationTest {
	@Autowired MockMvc mockMvc;
	@Autowired ObjectMapper objectMapper;
	@Autowired TestDataUtils testDataUtils;
	@Autowired
	NewsRepository newsRepository;
	int newsCnt = 10;

	@BeforeEach
	void beforeEach() {
		for (NewsCategory category : NewsCategory.values()) {
			testDataUtils.saveNewsList(category, LocalDateTime.now(), newsCnt);
		}
	}

	@Nested
	@DisplayName("getHotNewsList 메서드 테스트")
	class GetHotNewsList {
		@Test
		@DisplayName("success")
		void success() throws Exception {
			// given
			String url = "/news-habit/issue";
			// when, then
			String contentAsString = mockMvc.perform(get(url))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
			System.out.println(contentAsString);
			HotNewsResponseDtoList responseDto = objectMapper.readValue(contentAsString, HotNewsResponseDtoList.class);
			Assertions.assertThat(responseDto.getHotNewsResponseDtoList().size()).isEqualTo(newsCnt);
		}
	}

	@Nested
	@DisplayName("getRecommendedNewsList 메서드 테스트")
	class GetRecommendedNewsList {
		@ParameterizedTest
		@ValueSource(ints = {3, 4, 5})
		@DisplayName("success")
		void success(int cnt) throws Exception {
			// given
			String query = testDataUtils.getRecommendedNewsQueryString(cnt);
			String url = "/news-habit/recommendation?" + query + "&&cnt=" + cnt;
			// when, then
			String contentAsString = mockMvc.perform(get(url))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

			RecommendedNewsResponseDtoList responseDto = objectMapper.readValue(contentAsString, RecommendedNewsResponseDtoList.class);
			Assertions.assertThat(responseDto.getRecommendedNewsResponseDtoList().size()).isEqualTo(cnt);
			// 개수 체크
		}

		@ParameterizedTest
		@ValueSource(ints = {1, 2})
		@DisplayName("3개 보다 적은 선택 개수 수")
		void fail1(int cnt) throws Exception {
			// given
			String query = testDataUtils.getRecommendedNewsQueryString(cnt);
			String url = "/news-habit/recommendation?" + query + "&&cnt=" + cnt;
			// when, then
			String contentAsString = mockMvc.perform(get(url))
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
			System.out.println(contentAsString);
		}

		@Test
		@DisplayName("잘못된 쿼리 파라미터")
		void fail2() throws Exception {
			// given
			String url = "/news-habit/recommendation?something=wrong";
			// when, then
			String contentAsString = mockMvc.perform(get(url))
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
			System.out.println(contentAsString);
		}

		@Test
		@DisplayName("카테고리 개수 6개 초과")
		void fail3() throws Exception {
			// given
			String url = "/news-habit/recommendation?categories=HOT&&categories=POLITICS&&"
				+ "categories=ECONOMY&&categories=SOCIETY&&categories=LIFESTYLE_CULTURE&&"
				+ "categories=IT_SCIENCE&&categories=WORLD&&cnt=4";
			// when, then
			String contentAsString = mockMvc.perform(get(url))
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
			System.out.println(contentAsString);
		}
	}
}
