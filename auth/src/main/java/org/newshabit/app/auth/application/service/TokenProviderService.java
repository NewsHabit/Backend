package org.newshabit.app.auth.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.auth.application.port.AuthRepositoryOutputPort;
import org.newshabit.app.auth.application.port.TokenProviderOutputPort;
import org.newshabit.app.auth.application.port.TokenProviderUseCase;
import org.newshabit.app.auth.domain.dto.LoginTokenPublishResponse;
import org.newshabit.app.auth.domain.dto.ReissueAccessTokenResponse;
import org.newshabit.app.auth.domain.entity.AuthEntity;
import org.newshabit.common.auth.application.port.TokenCheckerOutputPort;
import org.newshabit.common.auth.domain.model.CustomUserDetail;
import org.newshabit.app.common.domain.enums.UserRole;
import org.newshabit.common.auth.infrastructure.exception.AccessTokenException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenProviderService implements TokenProviderUseCase {
	private final TokenProviderOutputPort tokenProviderOutputPort;
	private final TokenCheckerOutputPort tokenCheckerOutputPort;
	private final AuthRepositoryOutputPort authRepositoryOutputPort;

	@Override
	public LoginTokenPublishResponse createLoginToken(String socialId, String deviceId, int userId, List<UserRole> roles) {
		String accessToken = tokenProviderOutputPort.createAccessToken(socialId, userId, deviceId, roles);
		String refreshToken = tokenProviderOutputPort.createRefreshToken(socialId, userId, deviceId, roles);

		Optional<AuthEntity> authEntityOptional = authRepositoryOutputPort.findBySocialIdAndDeviceId(userId, deviceId);

		AuthEntity authEntity;

		if (authEntityOptional.isPresent()) {
			authEntity = authEntityOptional.get();
			authEntity.setRefreshToken(refreshToken);
			authEntity.modifyPublishedAt();
		} else {
			authEntity = new AuthEntity(
				null,
				userId,
				deviceId,
				refreshToken,
				LocalDateTime.now()
			);
		}

		authRepositoryOutputPort.save(authEntity);

		return new LoginTokenPublishResponse(
			accessToken,
			refreshToken
		);
	}

	@Override
	public String createCustomServerToken(String socialId, int userId, String deviceId, List<UserRole> roles, long tokenValidityInMilliseconds) {
		return tokenProviderOutputPort.createCustomServerToken(
			socialId,
			userId,
			deviceId,
			roles,
			tokenValidityInMilliseconds
		);
	}

	@Override
	public ReissueAccessTokenResponse reissueAccessToken(String refreshToken) throws AccessTokenException {
		CustomUserDetail userDetail = tokenCheckerOutputPort.getUserDetail(refreshToken);
		String socialId = userDetail.getUsername();
		String deviceId = userDetail.getDeviceId();
		int userId = userDetail.getUserId();
		List<UserRole> roles = userDetail.getRoles().stream().map(UserRole::valueOf).collect(Collectors.toList());

		LoginTokenPublishResponse loginToken = createLoginToken(socialId, deviceId, userId, roles);

		return new ReissueAccessTokenResponse(
			loginToken.accessToken(),
			loginToken.refreshToken()
		);
	}
}
