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

	public UserEntity save(UserEntity user) {
		return userRepository.save(user);
	}

	@Override
	public Optional<UserEntity> findBySocialId(String socialId) {
		return Optional.of(userRepository.findUserEntityBySocialId(socialId));
	}
}
