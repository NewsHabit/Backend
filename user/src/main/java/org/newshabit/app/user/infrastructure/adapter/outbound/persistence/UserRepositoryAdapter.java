package org.newshabit.app.user.infrastructure.adapter.outbound.persistence;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity.UserEntity;
import org.newshabit.app.user.application.port.output.UserRepositoryOutputPort;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryOutputPort {
	private final UserRepository userRepository;

	public UserEntity save(UserEntity user) {
		return userRepository.save(user);
	}

	@Override
	public Optional<UserEntity> findBySocialId(String socialId) {
		return Optional.ofNullable(userRepository.findUserEntityBySocialId(socialId));
	}
}
