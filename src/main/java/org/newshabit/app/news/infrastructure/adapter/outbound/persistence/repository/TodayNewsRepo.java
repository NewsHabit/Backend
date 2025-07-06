package org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.TodayNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodayNewsRepo extends JpaRepository<TodayNewsEntity, Integer> {

	List<TodayNewsEntity> findByUserIdAndPublishedAt(Integer userId, LocalDate publishedAt);

	boolean existsByUserIdAndNewsId(Integer userId, Integer newsId);
}
