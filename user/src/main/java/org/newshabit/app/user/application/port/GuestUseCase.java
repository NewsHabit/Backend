package org.newshabit.app.user.application.port;

import org.newshabit.app.user.domain.entity.UserEntity;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface GuestUseCase {
	void login(UserEntity user) throws NotFoundException;
	void register();
}
