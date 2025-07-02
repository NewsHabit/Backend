package org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository;

import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.TodayNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodayNewsRepo extends JpaRepository<TodayNewsEntity, Integer> {
	boolean existsByNewsId(Integer newsId);
}
