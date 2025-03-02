package org.newshabit.app.pubsub.infrastructure.repository;

import org.newshabit.app.common.domain.model.RefinedNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefinedNewsRepository extends JpaRepository<RefinedNews, Long> {

	boolean existsByOriginalUrl(String url);
}
