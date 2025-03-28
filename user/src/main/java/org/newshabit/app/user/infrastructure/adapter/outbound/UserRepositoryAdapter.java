package org.newshabit.app.user.infrastructure.adapter.outbound;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.user.domain.entity.UserEntity;
import org.newshabit.app.user.application.port.UserRepositoryOutputPort;
import org.newshabit.app.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryOutputPort {
	private final UserRepository userRepository;

	public Optional<UserEntity> save(UserEntity user) {
		return Optional.of(userRepository.save(user));
	}
}
