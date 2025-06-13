package org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository;


import java.time.LocalDateTime;
import java.util.List;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.RefinedNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RefinedNewsRepository extends JpaRepository<RefinedNewsEntity, Long> {

        boolean existsByOriginalUrl(String url);

        @Query("select n from RefinedNewsEntity n where (n.clickCnt < :cnt or n.publishedAt < :border) and not exists (select 1 from BookmarkEntity b where b.newsId = n.id)")
        List<RefinedNewsEntity> findDeletableNews(@Param("cnt") int clickCnt, @Param("border") LocalDateTime border);
}
