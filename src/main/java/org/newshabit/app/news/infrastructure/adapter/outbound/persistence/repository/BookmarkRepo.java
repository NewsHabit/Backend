package org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository;

import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.BookmarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepo extends JpaRepository<BookmarkEntity, Integer> {
    boolean existsByNewsId(Integer newsId);
}
