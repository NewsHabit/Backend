package org.newshabit.app.user.application.port;

import java.util.Optional;
import org.newshabit.app.user.domain.entity.UserEntity;

public interface UserRepositoryOutputPort {
	public Optional<UserEntity> save(UserEntity user);
}
