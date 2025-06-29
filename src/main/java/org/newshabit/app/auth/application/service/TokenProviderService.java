package org.newshabit.app.auth.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.auth.application.port.output.AuthRepositoryOutputPort;
import org.newshabit.app.auth.application.port.output.TokenProviderOutputPort;
import org.newshabit.app.auth.application.port.input.TokenProviderUseCase;
import org.newshabit.app.auth.infrastructure.adapter.inbound.web.dto.ReissueAccessTokenResponse;
import org.newshabit.app.auth.domain.model.Token;
import org.newshabit.app.auth.infrastructure.adapter.outbound.persistence.entity.AuthEntity;
import org.newshabit.app.auth.application.port.output.TokenCheckerOutputPort;
import org.newshabit.app.auth.domain.model.CustomUserDetail;
import org.newshabit.app.common.domain.enums.UserRole;
import org.newshabit.app.auth.common.exception.AccessTokenException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenProviderService implements TokenProviderUseCase {
	private final TokenProviderOutputPort tokenProviderOutputPort;
	private final TokenCheckerOutputPort tokenCheckerOutputPort;
	private final AuthRepositoryOutputPort authRepositoryOutputPort;

	@Override
	public Token createLoginToken(String socialId, String deviceId, int userId, List<UserRole> roles) {
		String accessToken = tokenProviderOutputPort.createAccessToken(socialId, userId, deviceId, roles);
		String refreshToken = tokenProviderOutputPort.createRefreshToken(socialId, userId, deviceId, roles);

                Optional<AuthEntity> authEntityOptional = authRepositoryOutputPort.findByUserIdAndDeviceId(userId, deviceId);

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

		return new Token(
			accessToken,
			refreshToken
		);
	}

	@Override
	public ReissueAccessTokenResponse reissueAccessToken(String refreshToken) throws AccessTokenException {
		CustomUserDetail userDetail = tokenCheckerOutputPort.getUserDetail(refreshToken);
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
