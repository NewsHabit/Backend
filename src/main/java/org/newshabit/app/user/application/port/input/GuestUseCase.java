package org.newshabit.app.user.application.port.input;

import org.newshabit.app.user.domain.model.Register;
import org.newshabit.app.user.common.exception.DuplicatedException;
import org.newshabit.app.auth.domain.model.Token;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface GuestUseCase {
	Token login(String socialId, String deviceId) throws NotFoundException;
	void register(Register register) throws DuplicatedException;
}
