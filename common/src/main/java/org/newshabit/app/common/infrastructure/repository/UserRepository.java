package org.newshabit.app.common.infrastructure.repository;

import java.util.Optional;
import org.newshabit.app.common.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByAuthToken(String authToken);
}
