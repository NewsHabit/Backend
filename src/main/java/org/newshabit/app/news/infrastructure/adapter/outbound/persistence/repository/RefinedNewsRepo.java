package org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository;


import java.time.LocalDate;
import java.util.List;
import org.newshabit.app.common.domain.enums.NewsCategory;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.RefinedNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RefinedNewsRepo extends JpaRepository<RefinedNewsEntity, Integer> {

        boolean existsByOriginalUrl(String url);

        @Modifying(clearAutomatically = true, flushAutomatically = true)
        @Query("DELETE FROM RefinedNewsEntity n " +
               "WHERE n.clickCnt < :clickCntThreshold " +
               "  OR FUNCTION('DATE', n.publishedAt) < :thresholdDay")
        int deleteAllBelowThreshold(@Param("clickCntThreshold") int clickCntThreshold, @Param("thresholdDay") LocalDate thresholdDay);

        @Modifying(clearAutomatically = true, flushAutomatically = true)
        @Query(value = "UPDATE refined_news " +
                "SET click_cnt = FLOOR(click_cnt * 0.9)", nativeQuery = true)
        int updateAllClickCntAfterDeletion();

        @Query("SELECT rne " +
			   "FROM RefinedNewsEntity rne " +
			   "WHERE rne.newsCategory IN :categories " +
			   "  AND rne.publishedAt >= :startOfDay " +
			   "  AND rne.id NOT IN ( " +
			   "    SELECT tne.newsId " +
			   "    FROM TodayNewsEntity tne " +
			   "    WHERE tne.userId = :userId AND tne.publishedAt >= :startOfDay" +
               "  )"
        )
        List<RefinedNewsEntity> findTodayNewsCandidates(
            @Param("userId")     Integer userId,
            @Param("categories") List<NewsCategory> categories,
            @Param("startOfDay") LocalDate startOfDay
        );
}
