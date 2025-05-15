package org.newshabit.app.user.application.port.input;

import org.newshabit.app.user.infrastructure.adapter.inbound.web.dto.LoginRequest;
import org.newshabit.app.user.infrastructure.adapter.inbound.web.dto.LoginResponse;
import org.newshabit.app.user.infrastructure.adapter.inbound.web.dto.RegisterRequest;
import org.newshabit.app.user.common.exception.DuplicatedException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface GuestUseCase {
	LoginResponse login(LoginRequest loginRequest) throws NotFoundException;
	void register(RegisterRequest registerRequest) throws DuplicatedException;
}
