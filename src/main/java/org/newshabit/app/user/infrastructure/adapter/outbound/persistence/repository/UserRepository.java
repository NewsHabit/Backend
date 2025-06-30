package org.newshabit.app.user.infrastructure.adapter.outbound.persistence.repository;

import java.util.Optional;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	Optional<UserEntity> findUserEntityBySocialId(String socialId);
}
