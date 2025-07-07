package org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository;

import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.BookmarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepo extends JpaRepository<BookmarkEntity, Integer> {
    boolean existsByNewsId(Integer newsId);

    List<BookmarkEntity> findAllByUserId(Integer userId);
}
