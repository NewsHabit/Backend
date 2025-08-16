package org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.NewsReadLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsReadLogRepo extends JpaRepository<NewsReadLogEntity, Integer> {
	Optional<NewsReadLogEntity> findByNewsIdAndUserId(Integer newsId, Integer userId);

	@Query("""
        SELECT url
        FROM NewsReadLogEntity url
        WHERE url.userId = :userId
          AND url.publishedAt BETWEEN :startDate AND :endDate
        """)
	List<NewsReadLogEntity> findOverlappingNewsReadLog(
			@Param("userId")    int userId,
			@Param("startDate") LocalDate startDate,
			@Param("endDate")   LocalDate endDate
	);

	boolean existsByUserIdAndNewsId(Integer userId, Integer newsId);
}
