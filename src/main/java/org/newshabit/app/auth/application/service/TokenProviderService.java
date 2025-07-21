package org.newshabit.app.auth.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.auth.application.port.output.AuthRepoPort;
import org.newshabit.app.auth.application.port.output.TokenProviderPort;
import org.newshabit.app.auth.application.port.input.TokenProviderUseCase;
import org.newshabit.app.auth.domain.model.Auth;
import org.newshabit.app.auth.infrastructure.adapter.inbound.web.dto.ReissueAccessTokenResponse;
import org.newshabit.app.auth.domain.model.Token;
import org.newshabit.app.auth.application.port.output.TokenCheckerPort;
import org.newshabit.app.auth.domain.model.CustomUserDetail;
import org.newshabit.app.common.domain.enums.UserRole;
import org.newshabit.app.auth.common.exception.AccessTokenException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenProviderService implements TokenProviderUseCase {
	private final TokenProviderPort tokenProviderPort;
	private final TokenCheckerPort tokenCheckerPort;
	private final AuthRepoPort authRepoPort;

	@Override
	public Token createLoginToken(String socialId, String deviceId, int userId, List<UserRole> roles) {
		String accessToken = tokenProviderPort.createAccessToken(socialId, userId, deviceId, roles);
		String refreshToken = tokenProviderPort.createRefreshToken(socialId, userId, deviceId, roles);

		Optional<Auth> authOptional = authRepoPort.findByUserIdAndDeviceId(userId, deviceId);

		Auth auth = new Auth(
			authOptional.map(Auth::getId).orElse(null),
			userId,
			deviceId,
			refreshToken,
			LocalDateTime.now()
		);

		authRepoPort.save(auth);

		return new Token(
			accessToken,
			refreshToken
		);
	}

	@Override
	public ReissueAccessTokenResponse reissueAccessToken(String refreshToken) throws AccessTokenException {
		CustomUserDetail userDetail = tokenCheckerPort.getUserDetail(refreshToken);
		String socialId = userDetail.getUsername();
		String deviceId = userDetail.getDeviceId();
		int userId = userDetail.getUserId();
		List<UserRole> roles = userDetail.getRoles().stream().map(UserRole::valueOf).collect(Collectors.toList());

		Token token = createLoginToken(socialId, deviceId, userId, roles);

		return new ReissueAccessTokenResponse(
			token.getAccessToken(),
			token.getRefreshToken()
		);
	}
}
