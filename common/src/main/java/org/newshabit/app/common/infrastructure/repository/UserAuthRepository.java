package org.newshabit.app.common.infrastructure.repository;

import java.util.Optional;
import org.newshabit.app.common.domain.entity.UserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuthEntity, Long> {
	Optional<UserAuthEntity> findByAccessToken(String accessToken);
}
