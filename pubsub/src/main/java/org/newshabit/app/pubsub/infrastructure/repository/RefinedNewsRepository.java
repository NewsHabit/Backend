package org.newshabit.app.pubsub.infrastructure.repository;

import org.newshabit.app.pubsub.domain.entity.RefinedNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefinedNewsRepository extends JpaRepository<RefinedNewsEntity, Long> {

	boolean existsByOriginalUrl(String url);
}
