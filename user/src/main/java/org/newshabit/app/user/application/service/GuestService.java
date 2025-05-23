package org.newshabit.app.user.application.service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.auth.application.port.input.TokenProviderUseCase;
import org.newshabit.app.user.application.port.output.UserDailyGoalOutputPort;
import org.newshabit.app.user.domain.model.Register;
import org.newshabit.app.user.domain.model.User;
import org.newshabit.app.user.domain.model.UserDailyGoal;
import org.newshabit.common.auth.domain.model.Token;
import org.newshabit.app.common.domain.enums.UserRole;
import org.newshabit.app.user.common.exception.ErrorCode;
import org.newshabit.app.user.common.exception.DuplicatedException;
import org.newshabit.app.user.common.exception.NotFoundException;
import org.newshabit.app.user.application.port.input.GuestUseCase;
import org.newshabit.app.user.application.port.output.UserRepositoryOutputPort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestService implements GuestUseCase {
	private final UserRepositoryOutputPort userRepositoryOutputPort;
	private final UserDailyGoalOutputPort userDailyGoalOutputPort;
	private final TokenProviderUseCase tokenProviderUseCase;

	@Override
	public Token login(String socialId, String deviceId) throws NotFoundException {
		Optional<User> userOptional = userRepositoryOutputPort.findBySocialId(socialId);

		if (userOptional.isEmpty()) {
			throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
		}

		User user = userOptional.get();

		List<UserRole> roles = List.of(user.getRole());

		Token token = tokenProviderUseCase.createLoginToken(
			socialId, deviceId, user.getId(), roles
		);

		return new Token(token.getAccessToken(), token.getRefreshToken());
	}

	@Override
	@Transactional
	public void register(Register register) throws DuplicatedException {
		userRepositoryOutputPort.findBySocialId(register.getSocialId()).ifPresent( exist -> {
			throw new DuplicatedException(ErrorCode.DUPLICATED_USER);
		});

		User user = new User(
			null,
			register.getUsername(),
			LocalDateTime.now(),
			register.getCategoryList(),
			register.getSocialId(),
			UserRole.MEMBER
		);

		user = userRepositoryOutputPort.save(user);

		UserDailyGoal userDailyGoal = new UserDailyGoal(
			register.getDailyGoal(),
			LocalDate.now(),
			null,
			user.getId()
		);

		userDailyGoalOutputPort.save(userDailyGoal);
	}
}
