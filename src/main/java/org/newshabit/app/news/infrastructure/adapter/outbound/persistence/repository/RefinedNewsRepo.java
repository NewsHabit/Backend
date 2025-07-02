package org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository;


import java.time.LocalDateTime;
import java.util.List;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.RefinedNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RefinedNewsRepo extends JpaRepository<RefinedNewsEntity, Integer> {

        boolean existsByOriginalUrl(String url);

        @Query("""
                SELECT n FROM RefinedNewsEntity n
                LEFT JOIN BookmarkEntity b ON n.id = b.newsId
                WHERE (n.clickCnt < :cnt OR n.publishedAt < :border)
                AND b.id IS NULL
                """)
        List<RefinedNewsEntity> findDeletableNews(
                @Param("cnt") int clickCnt,
                @Param("border") LocalDateTime border
        );
}
