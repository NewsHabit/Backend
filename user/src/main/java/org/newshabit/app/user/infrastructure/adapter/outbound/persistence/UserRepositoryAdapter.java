package org.newshabit.app.user.infrastructure.adapter.outbound.persistence;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.common.domain.enums.NewsCategory;
import org.newshabit.app.user.common.exception.NotFoundException;
import org.newshabit.app.user.domain.model.User;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity.UserEntity;
import org.newshabit.app.user.application.port.output.UserRepositoryOutputPort;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity.mapper.EntityMapper;
import org.newshabit.app.user.infrastructure.adapter.outbound.persistence.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryOutputPort {
	private final UserRepository userRepository;
	private final EntityMapper entityMapper;

	public User save(User user) {
		UserEntity userEntity = entityMapper.toEntity(user);

		return entityMapper.toDomain(userRepository.save(userEntity));
	}

	@Override
	public Optional<User> findBySocialId(String socialId) {
		Optional<UserEntity> userEntity = userRepository.findUserEntityBySocialId(socialId);

		return userEntity.map(entityMapper::toDomain);
	}

	@Override
	public Optional<User> findByUserId(int id) {
		Optional<UserEntity> optional = userRepository.findById(id);

		if (optional.isEmpty())	{
			throw new NotFoundException();
		}

		return optional.map(entityMapper::toDomain);
	}

	@Override
	public void updateUsername(User user, String username) {
		UserEntity userEntity = userRepository.findById(user.getId()).orElseThrow(NotFoundException::new);

		userEntity.updateUsername(username);

		userRepository.save(userEntity);
	}

	@Override
	public void updateInterestCategories(User user, List<NewsCategory> interestCategories) throws NotFoundException {
		UserEntity userEntity = userRepository.findById(user.getId()).orElseThrow(NotFoundException::new);

		userEntity.updateInterestCategories(interestCategories);

		userRepository.save(userEntity);
	}
}
