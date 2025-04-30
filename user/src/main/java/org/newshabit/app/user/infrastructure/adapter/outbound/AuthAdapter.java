package org.newshabit.app.user.infrastructure.adapter.outbound;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.common.response.CommonResponse;
import org.newshabit.app.user.application.port.AuthOutputPort;
import org.newshabit.app.user.domain.dto.LoginTokenPublishRequest;
import org.newshabit.app.user.domain.dto.LoginTokenPublishResponse;
import org.newshabit.app.user.infrastructure.client.AuthClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthAdapter implements AuthOutputPort {
	private final AuthClient authClient;

	@Override
	public LoginTokenPublishResponse getTokens(LoginTokenPublishRequest request) {
		CommonResponse<LoginTokenPublishResponse> response = authClient.getTokens(request);

		if (response.getStatus() != HttpStatus.OK.value()) {
			log.error("auth service error: {}", response.getStatus());
			throw new RuntimeException("loginTokenPublish");
		}

		return response.getData();
	}
}
