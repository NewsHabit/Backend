package org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository;

import java.util.Optional;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.NewsReadLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface NewsReadLogRepository extends JpaRepository<NewsReadLogEntity, Integer> {
	Optional<NewsReadLogEntity> findByNewsIdAndUserId(Integer newsId, Integer userId);
}
