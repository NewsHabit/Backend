package org.newshabit.app.user.application.port;

import org.newshabit.app.user.domain.dto.LoginTokenPublishRequest;
import org.newshabit.app.user.domain.dto.LoginTokenPublishResponse;

public interface AuthOutputPort {
	LoginTokenPublishResponse getTokens(LoginTokenPublishRequest request);
}
