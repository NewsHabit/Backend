package org.newshabit.app.user.application.port;

import org.newshabit.app.user.domain.dto.LoginRequest;
import org.newshabit.app.user.domain.dto.LoginResponse;
import org.newshabit.app.user.domain.dto.RegisterRequest;
import org.newshabit.app.user.exception.DuplicatedException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface GuestUseCase {
	LoginResponse login(LoginRequest loginRequest) throws NotFoundException;
	void register(RegisterRequest registerRequest) throws DuplicatedException;
}
