package org.newshabit.app.auth.application.port.output;

import java.util.Optional;
import org.newshabit.app.auth.infrastructure.adapter.outbound.persistence.entity.AuthEntity;

public interface AuthRepositoryOutputPort {
	void save(AuthEntity entity);
    Optional<AuthEntity> findByUserIdAndDeviceId(int userId, String deviceId);
}
