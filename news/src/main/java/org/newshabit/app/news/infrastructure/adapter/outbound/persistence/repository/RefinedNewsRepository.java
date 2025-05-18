package org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository;


import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.RefinedNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefinedNewsRepository extends JpaRepository<RefinedNewsEntity, Long> {

	boolean existsByOriginalUrl(String url);
}
