package org.newshabit.app.user.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.auth.application.port.TokenProviderUseCase;
import org.newshabit.app.common.domain.enums.UserRole;
import org.newshabit.app.user.domain.dto.LoginRequest;
import org.newshabit.app.user.domain.dto.LoginResponse;
import org.newshabit.app.user.domain.dto.RegisterRequest;
import org.newshabit.app.user.domain.entity.AuthEntity;
import org.newshabit.app.user.domain.entity.UserDailyGoalLogEntity;
import org.newshabit.app.user.domain.entity.UserEntity;
import org.newshabit.app.user.exception.DuplicatedException;
import org.newshabit.app.user.exception.NotFoundException;
import org.newshabit.app.user.application.port.GuestUseCase;
import org.newshabit.app.user.application.port.UserRepositoryOutputPort;

import org.newshabit.app.user.exception.NotFoundException.ErrorMessage;
import org.newshabit.app.user.infrastructure.repository.UserDailyGoalLogRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestService implements GuestUseCase {
	private final UserRepositoryOutputPort userRepositoryOutputPort;
	private final TokenProviderUseCase tokenProviderUseCase;
	private final UserDailyGoalLogRepository userDailyGoalLogRepository;

	@Override
	public LoginResponse login(LoginRequest loginRequest) throws NotFoundException {
		Optional<UserEntity> userEntityOptional = userRepositoryOutputPort.findBySocialId(loginRequest.socialId());

		if (userEntityOptional.isEmpty()) {
			throw new NotFoundException(ErrorMessage.USER_NOT_FOUND);
		}

		UserEntity userEntity = userEntityOptional.get();

		List<UserRole> roles = List.of(userEntity.getRole());

		String accessToken = tokenProviderUseCase.createAccessToken(userEntity.getSocialId(), roles);
		String refreshToken = tokenProviderUseCase.createRefreshToken(userEntity.getSocialId(), roles);

		AuthEntity newAuthEntity = new AuthEntity(
			null,
			userEntity,
			loginRequest.deviceId(),
			refreshToken,
			LocalDateTime.now()
		);

		userEntity.getAuthList().add(newAuthEntity);

		userRepositoryOutputPort.save(userEntity);

		return new LoginResponse(accessToken, refreshToken);
	}

	@Override
	public void register(RegisterRequest registerRequest) throws DuplicatedException {
		Optional<UserEntity> userEntityOptional = userRepositoryOutputPort.findBySocialId(registerRequest.socialId());

		userEntityOptional.ifPresent( a -> {throw new DuplicatedException(
			DuplicatedException.ErrorMessage.USER_ALREADY_EXIST);});

		UserEntity userEntity = userRepositoryOutputPort.save(UserEntity.from(registerRequest));

		UserDailyGoalLogEntity userDailyGoalLogEntity = UserDailyGoalLogEntity.create(
			registerRequest.dailyGoal(),
			LocalDate.now(),
			userEntity
		);

		userDailyGoalLogRepository.save(userDailyGoalLogEntity);
	}
}
