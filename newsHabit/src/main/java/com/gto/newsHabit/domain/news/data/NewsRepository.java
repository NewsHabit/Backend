package com.gto.newsHabit.domain.news.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gto.newsHabit.data.News;
import com.gto.newsHabit.data.type.NewsCategory;

public interface NewsRepository extends JpaRepository<News, String> {
	List<News> findAllByCategory(NewsCategory category);
	List<News> findAllByCategoryIn(List<NewsCategory> categories);
}
