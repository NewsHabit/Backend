package org.newshabit.app.auth.infrastructure.adapter.outbound;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.auth.application.port.AuthRepositoryOutputPort;
import org.newshabit.app.auth.domain.entity.AuthEntity;
import org.newshabit.app.auth.infrastructure.repository.AuthRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthRepositoryAdapter implements AuthRepositoryOutputPort {
	private final AuthRepository authRepository;

	public Optional<AuthEntity> findBySocialIdAndDeviceId(int userId, String deviceId) {
		return authRepository.findAuthEntityByUserIdAndDeviceId(userId, deviceId);
	}

	public void save(AuthEntity entity) {
		authRepository.save(entity);
	}
}
