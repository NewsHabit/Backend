package org.newshabit.app.user.application.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.common.domain.enums.UserRole;
import org.newshabit.app.user.application.port.AuthOutputPort;
import org.newshabit.app.user.common.exception.ErrorCode;
import org.newshabit.app.user.domain.dto.LoginRequest;
import org.newshabit.app.user.domain.dto.LoginResponse;
import org.newshabit.app.user.domain.dto.LoginTokenPublishRequest;
import org.newshabit.app.user.domain.dto.LoginTokenPublishResponse;
import org.newshabit.app.user.domain.dto.RegisterRequest;
import org.newshabit.app.user.domain.entity.UserDailyGoalLogEntity;
import org.newshabit.app.user.domain.entity.UserEntity;
import org.newshabit.app.user.common.exception.DuplicatedException;
import org.newshabit.app.user.common.exception.NotFoundException;
import org.newshabit.app.user.application.port.GuestUseCase;
import org.newshabit.app.user.application.port.UserRepositoryOutputPort;

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
			throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
		}

		UserEntity userEntity = userEntityOptional.get();

		List<UserRole> roles = List.of(userEntity.getRole());

		LoginTokenPublishResponse publishedToken = authOutputPort.getTokens(
			new LoginTokenPublishRequest(loginRequest.socialId(), loginRequest.deviceId(), userEntity.getId(), roles)
		);

		return new LoginResponse(publishedToken.accessToken(), publishedToken.refreshToken());
	}

	@Override
	public void register(RegisterRequest registerRequest) throws DuplicatedException {
		Optional<UserEntity> userEntityOptional = userRepositoryOutputPort.findBySocialId(registerRequest.socialId());

		userEntityOptional.ifPresent( a -> {
			throw new DuplicatedException(ErrorCode.DUPLICATED_USER);
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
