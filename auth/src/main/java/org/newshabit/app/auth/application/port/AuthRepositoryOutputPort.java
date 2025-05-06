package org.newshabit.app.auth.application.port;

import java.util.Optional;
import org.newshabit.app.auth.domain.entity.AuthEntity;

public interface AuthRepositoryOutputPort {
	void save(AuthEntity entity);
	Optional<AuthEntity> findBySocialIdAndDeviceId(int userId, String deviceId);
}
