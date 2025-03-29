package org.newshabit.app.user.infrastructure.repository;

import org.newshabit.app.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findUserEntityBySocialId(String socialId);
}
