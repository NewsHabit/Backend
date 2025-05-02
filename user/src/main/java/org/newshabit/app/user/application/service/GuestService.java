package org.newshabit.app.user.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.common.domain.enums.UserRole;
import org.newshabit.app.user.application.port.AuthOutputPort;
import org.newshabit.app.user.domain.dto.LoginRequest;
import org.newshabit.app.user.domain.dto.LoginResponse;
import org.newshabit.app.user.domain.dto.LoginTokenPublishRequest;
import org.newshabit.app.user.domain.dto.LoginTokenPublishResponse;
import org.newshabit.app.user.domain.dto.RegisterRequest;
import org.newshabit.app.user.domain.entity.AuthEntity;
import org.newshabit.app.user.domain.entity.UserDailyGoalLogEntity;
import org.newshabit.app.user.domain.entity.UserEntity;
import org.newshabit.app.user.common.exception.DuplicatedException;
import org.newshabit.app.user.common.exception.NotFoundException;
import org.newshabit.app.user.application.port.GuestUseCase;
import org.newshabit.app.user.application.port.UserRepositoryOutputPort;

import org.newshabit.app.user.common.exception.NotFoundException.ErrorMessage;
import org.newshabit.app.user.infrastructure.repository.UserDailyGoalLogRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestService implements GuestUseCase {
	private final UserRepositoryOutputPort userRepositoryOutputPort;
	private final UserDailyGoalLogRepository userDailyGoalLogRepository;
	private final AuthOutputPort authOutputPort;

	@Override
	public LoginResponse login(LoginRequest loginRequest) throws NotFoundException {
		Optional<UserEntity> userEntityOptional = userRepositoryOutputPort.findBySocialId(loginRequest.socialId());

		if (userEntityOptional.isEmpty()) {
			throw new NotFoundException(ErrorMessage.USER_NOT_FOUND);
		}

		UserEntity userEntity = userEntityOptional.get();

		if (userEntity.getAuthList().stream()
			.anyMatch(authEntity -> authEntity.getDeviceId().equals(loginRequest.deviceId()))) {
			throw new DuplicatedException(DuplicatedException.ErrorMessage.DUPLICATED_DEVICE);
		}

		List<UserRole> roles = List.of(userEntity.getRole());

		LoginTokenPublishResponse publishedToken = authOutputPort.getTokens(
			new LoginTokenPublishRequest(userEntity.getSocialId(), roles)
		);

		AuthEntity newAuthEntity = new AuthEntity(
			null,
			userEntity,
			loginRequest.deviceId(),
			publishedToken.refreshToken(),
			LocalDateTime.now()
		);

		userEntity.getAuthList().add(newAuthEntity);

		userRepositoryOutputPort.save(userEntity);

		return new LoginResponse(publishedToken.accessToken(), publishedToken.refreshToken());
	}

	@Override
	public void register(RegisterRequest registerRequest) throws DuplicatedException {
		Optional<UserEntity> userEntityOptional = userRepositoryOutputPort.findBySocialId(registerRequest.socialId());

		userEntityOptional.ifPresent( a -> {
			throw new DuplicatedException(DuplicatedException.ErrorMessage.DUPLICATED_USER);
		});

		UserEntity userEntity = userRepositoryOutputPort.save(UserEntity.from(registerRequest));

		UserDailyGoalLogEntity userDailyGoalLogEntity = UserDailyGoalLogEntity.create(
			registerRequest.dailyGoal(),
			LocalDate.now(),
			userEntity
		);

		userDailyGoalLogRepository.save(userDailyGoalLogEntity);
	}
}
