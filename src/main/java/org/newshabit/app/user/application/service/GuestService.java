package org.newshabit.app.user.application.service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.auth.application.port.input.TokenProviderUseCase;
import org.newshabit.app.auth.application.port.output.AuthRepoPort;
import org.newshabit.app.auth.domain.model.Auth;
import org.newshabit.app.user.application.port.output.UserDailyGoalOutputPort;
import org.newshabit.app.user.domain.model.Register;
import org.newshabit.app.user.domain.model.User;
import org.newshabit.app.user.domain.model.UserDailyGoal;
import org.newshabit.app.auth.domain.model.Token;
import org.newshabit.app.common.domain.enums.UserRole;
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
	private final AuthRepoPort authRepoPort;

	@Override
	public Token login(String socialId, String deviceId) throws NotFoundException {
		Optional<User> userOptional = userRepositoryOutputPort.findBySocialId(socialId);

		if (userOptional.isEmpty()) {
			throw new NotFoundException();
		}

		User user = userOptional.get();

		List<UserRole> roles = List.of(user.getRole());

		Token token = tokenProviderUseCase.createLoginToken(
			socialId, deviceId, user.getId(), roles
		);

		Auth auth = new Auth(
			null,
			user.getId(),
			deviceId,
			token.getRefreshToken(),
			LocalDateTime.now()
		);

		authRepoPort.save(auth);

		return new Token(token.getAccessToken(), token.getRefreshToken());
	}

	@Override
	@Transactional
	public void register(Register register) throws DuplicatedException {
		userRepositoryOutputPort.findBySocialId(register.getSocialId()).ifPresent( exist -> {
			throw new DuplicatedException();
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
			null,
			register.getDailyGoal(),
			LocalDate.now(),
			null,
			user.getId()
		);

		userDailyGoalOutputPort.save(userDailyGoal);
	}
}
