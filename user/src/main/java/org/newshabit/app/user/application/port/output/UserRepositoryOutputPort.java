package org.newshabit.app.user.application.port.output;

import java.util.Optional;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity.UserEntity;

public interface UserRepositoryOutputPort {
	UserEntity save(UserEntity user);
	Optional<UserEntity> findBySocialId(String socialId);
}
