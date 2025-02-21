package org.newshabit.app.aiProcessor.infrastructure.repository;

import java.util.Optional;
import org.newshabit.app.aiProcessor.domain.RefinedNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefinedNewsRepository extends JpaRepository<RefinedNews, Long> {

	Optional<Boolean> findByUrl(String url);
}
