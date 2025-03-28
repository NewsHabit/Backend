package org.newshabit.app.user.application.service;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.user.domain.entity.UserEntity;
import org.newshabit.app.user.exception.NotFoundException;
import org.newshabit.app.user.exception.NotFoundException.ErrorMessage;
import org.newshabit.app.user.application.port.GuestUseCase;
import org.newshabit.app.user.application.port.UserRepositoryOutputPort;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestService implements GuestUseCase {
	private final UserRepositoryOutputPort userRepositoryOutputPort;

	@Override
	public void login(UserEntity user) throws ChangeSetPersister.NotFoundException {
		if (user == null) {
			throw new NotFoundException(ErrorMessage.USER_NOT_FOUND);
		}

		createJwtToken();

		userRepositoryOutputPort.save(user);
	}

	@Override
	public void register() {

	}
}
